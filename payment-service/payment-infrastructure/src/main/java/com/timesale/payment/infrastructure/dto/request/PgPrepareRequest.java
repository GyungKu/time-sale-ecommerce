package com.timesale.payment.infrastructure.dto.request;

public record PgPrepareRequest(
    String orderId,
    Integer amount
) {

}
