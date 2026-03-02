package com.timesale.payment.domain.port;

import com.timesale.payment.domain.Payment;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository {

    void save(Payment payment);

    Optional<Payment> findByOrderId(Long orderId);

    List<Payment> findExpiredPendingPayments(LocalDateTime timeoutLimit);
}
