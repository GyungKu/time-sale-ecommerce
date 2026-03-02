package com.timesale.payment.application;

import com.timesale.common.exception.BusinessException;
import com.timesale.payment.application.dto.response.PgResponse;
import com.timesale.payment.application.port.OrderClient;
import com.timesale.payment.application.port.PgClient;
import com.timesale.payment.domain.Payment;
import com.timesale.payment.domain.Payment.PaymentStatus;
import com.timesale.payment.domain.exception.PaymentErrorCode;
import com.timesale.payment.domain.port.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PgClient pgClient;
    private final OrderClient orderClient;
    private final PaymentResultHandler paymentResultHandler;

    @Transactional
    public void preparePayment(Long orderId) {
        // 주문 서버에 진짜 결제 금액 물어보기
        Integer realAmount = orderClient.getOrder(orderId);

        // 가짜 PG서버에 사전 등록 요청
        pgClient.preparePayment(orderId, realAmount);

        // 우리 결제 DB에 READY(결제 대기) 저장
        Payment payment = Payment.prepare(orderId, realAmount);
        paymentRepository.save(payment);

        log.info("결제 사전 등록 완료 - orderId: {}, amount: {}", orderId, realAmount);
    }

    public PgResponse confirmPayment(Long orderId, Integer amount, String authKey) {
        Payment payment = getByOrderId(orderId);

        if (!payment.getStatus().equals(PaymentStatus.READY))
            throw new BusinessException(PaymentErrorCode.ALREADY_PAYMENT);

        if (!payment.getAmount().equals(amount)) {
            failPayment(payment);
            throw new BusinessException(PaymentErrorCode.INVALID_AMOUNT);
        }

        PgResponse response = pgClient.confirmPayment(payment, authKey);
        payment.confirm(response.receiptUrl());
        log.info("결제 최종 승인 완료! - orderId: {}", orderId);
        paymentResultHandler.saveAndPublish(payment);
        return response;
    }

    public void failPayment(Payment payment) {
        payment.fail();
        paymentResultHandler.saveAndPublish(payment);
    }

    private Payment getByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
            .orElseThrow(() -> new BusinessException(PaymentErrorCode.NOT_FOUND_BY_ORDER_ID));
    }

}
