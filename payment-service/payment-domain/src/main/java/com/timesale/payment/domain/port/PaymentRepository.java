package com.timesale.payment.domain.port;

import com.timesale.payment.domain.Payment;

public interface PaymentRepository {

    void save(Payment payment);

}
