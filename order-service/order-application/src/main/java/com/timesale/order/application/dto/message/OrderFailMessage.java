package com.timesale.order.application.dto.message;

public record OrderFailMessage(
    Long productId,
    Integer quantity
) {

}
