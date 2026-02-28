package com.timesale.payment.infrastructure.dto.response;

import com.timesale.payment.application.dto.response.PgResponse;

public record PgConfirmResponse(
    String orderId,
    Integer amount,
    String status,
    String receiptUrl
) {

    public PgResponse toPgResponse() {
        return new PgResponse(orderId, amount, status, receiptUrl);
    }

}
