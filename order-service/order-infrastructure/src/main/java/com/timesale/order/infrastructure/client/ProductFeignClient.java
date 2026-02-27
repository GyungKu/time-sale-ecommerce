package com.timesale.order.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-client", url = "http://localhost:8081")
public interface ProductFeignClient {

    @PostMapping("/api/v1/products/{id}/decrease-stock")
    void decreaseStock(@PathVariable("id") Long productId,
        @RequestParam("quantity") Integer quantity);

    @PostMapping("/api/v1/products/{id}/increase-stock")
    void increaseStock(@PathVariable("id") Long productId,
        @RequestParam("quantity") Integer quantity);

}
