package com.timesale.payment.api.dto.request;

public record ConfirmPaymentRequest(
    Long orderId,
    Integer amount,
    String authKey
) {

}
