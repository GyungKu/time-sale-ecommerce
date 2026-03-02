package com.timesale.payment.infrastructure.persistence;

import com.timesale.payment.domain.Payment;
import com.timesale.payment.domain.Payment.PaymentStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByOrderId(Long orderId);
    List<Payment> findTop100AllByStatusAndCreatedAtBeforeOrderByIdAsc(PaymentStatus status,
        LocalDateTime createdAt);
}
