package com.timesale.order.infrastructure.client;

import com.timesale.order.application.port.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductClientAdapter implements ProductClient {

    private final ProductFeignClient productFeignClient;

    @Override
    public void decreaseStock(Long productId, Integer quantity) {
        productFeignClient.decreaseStock(productId, quantity);
    }
}
