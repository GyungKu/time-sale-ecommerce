package com.timesale.payment.application.dto.message;

public record PaymentResultMessage(
    Long orderId,
    String status
) {

}
