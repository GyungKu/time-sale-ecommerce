package com.timesale.order.infrastructure.client;

import com.timesale.common.exception.BusinessException;
import com.timesale.order.application.port.ProductClient;
import com.timesale.order.infrastructure.exception.ExternalErrorCode;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductClientAdapter implements ProductClient {

    private final ProductFeignClient productFeignClient;

    @Override
    public void decreaseStock(Long productId, Integer quantity) {
        try {
            productFeignClient.decreaseStock(productId, quantity);
        } catch (FeignException e) {
            log.error("Product 서버 재고 차감 요청 실패: status={}, body={}",
                e.status(), e.contentUTF8());
            throw new BusinessException(ExternalErrorCode.EXTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void increaseStock(Long productId, Integer quantity) {
        try {
            productFeignClient.increaseStock(productId, quantity);
        } catch (FeignException e) {
            log.error("Product 서버 재고 복구 요청 실패: status={}, body={}",
                e.status(), e.contentUTF8());
            throw new BusinessException(ExternalErrorCode.EXTERNAL_SERVER_ERROR);
        }
    }
}
