package com.timesale.product.api.dto.message;

public record OrderFailMessage(
    Long productId,
    Integer quantity
) {

}
