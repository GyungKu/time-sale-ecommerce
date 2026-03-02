package com.timesale.payment.application;

import com.timesale.payment.domain.Payment;
import com.timesale.payment.domain.Payment.PaymentStatus;
import com.timesale.payment.domain.port.PaymentRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentFailScheduler {

    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;

    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void failPayments() {
        LocalDateTime timeoutLimit = LocalDateTime.now().minusMinutes(30);
        List<Payment> payments = paymentRepository.findExpiredPendingPayments(timeoutLimit);

        if (payments.isEmpty()) return;

        payments.forEach(paymentService::failPayment);
    }

}
