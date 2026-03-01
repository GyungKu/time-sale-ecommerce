package com.timesale.order.api.dto.message;

public record PaymentResultMessage(
    Long orderId,
    String status
) {

}
