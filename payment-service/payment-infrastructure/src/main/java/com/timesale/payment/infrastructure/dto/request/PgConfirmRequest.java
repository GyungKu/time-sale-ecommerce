package com.timesale.payment.infrastructure.dto.request;

public record PgConfirmRequest(
    String orderId,
    Integer amount,
    String authKey
) {

}
