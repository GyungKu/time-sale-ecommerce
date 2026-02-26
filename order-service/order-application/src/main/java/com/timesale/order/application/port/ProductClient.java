package com.timesale.order.application.port;

public interface ProductClient {
    void decreaseStock(Long productId, Integer quantity);
}
