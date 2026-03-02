package com.timesale.payment.application;

import com.timesale.payment.application.dto.message.PaymentResultMessage;
import com.timesale.payment.application.port.MessageQueuePort;
import com.timesale.payment.domain.Payment;
import com.timesale.payment.domain.port.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentResultHandler {

    private final MessageQueuePort messageQueue;
    private final PaymentRepository paymentRepository;

    @Transactional
    public void saveAndPublish(Payment payment) {
        log.info("Kafka 결제 완료 이벤트 직접 발행 요청 - orderId: {}", payment.getOrderId());
        paymentRepository.save(payment);
        messageQueue.publish("payment-events",
            new PaymentResultMessage(payment.getOrderId(), payment.getStatus().name()));
    }

}
