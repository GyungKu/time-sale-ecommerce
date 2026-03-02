package com.timesale.payment.infrastructure.persistence;

import com.timesale.payment.domain.Payment;
import com.timesale.payment.domain.Payment.PaymentStatus;
import com.timesale.payment.domain.port.PaymentRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public void save(Payment payment) {
        paymentJpaRepository.save(payment);
    }

    @Override
    public Optional<Payment> findByOrderId(Long orderId) {
        return paymentJpaRepository.findByOrderId(orderId);
    }

    @Override
    public List<Payment> findExpiredPendingPayments(LocalDateTime timeoutLimit) {
        return paymentJpaRepository.findTop100AllByStatusAndCreatedAtBeforeOrderByIdAsc(
            PaymentStatus.READY, timeoutLimit);
    }
}
