package com.timesale.product.application;

import com.timesale.product.application.port.DistributedLockPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductLockFacade {

    private final DistributedLockPort distributedLockPort;
    private final ProductService productService;

    public void decreaseStock(Long productId, Integer quantity) {
        String lockKey = "product:lock:" + productId;
        distributedLockPort.executeWithLock(lockKey, () ->
            productService.decreaseProductStock(productId, quantity));
    }

}
