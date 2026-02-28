package com.timesale.payment.domain.port;

import com.timesale.payment.domain.Payment;
import java.util.Optional;

public interface PaymentRepository {

    void save(Payment payment);

    Optional<Payment> findByOrderId(Long orderId);
}
