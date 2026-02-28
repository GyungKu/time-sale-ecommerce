package com.timesale.payment.application.dto.response;

public record PgResponse(
    String orderId,
    Integer amount,
    String status,
    String receiptUrl
) {

}
