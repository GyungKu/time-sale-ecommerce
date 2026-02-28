package com.timesale.payment.infrastructure.dto.response;

public record PgPrepareResponse(
    String orderId,
    String status
) {

}
