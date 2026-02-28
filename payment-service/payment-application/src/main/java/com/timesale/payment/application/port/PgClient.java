package com.timesale.payment.application.port;

import com.timesale.payment.application.dto.response.PgResponse;
import com.timesale.payment.domain.Payment;

public interface PgClient {

    void preparePayment(Long orderId, Integer amount);
    PgResponse confirmPayment(Payment payment, String authKey);
}
