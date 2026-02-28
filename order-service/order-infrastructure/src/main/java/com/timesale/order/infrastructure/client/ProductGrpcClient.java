package com.timesale.order.infrastructure.client;

import com.timesale.common.exception.BusinessException;
import com.timesale.common.proto.product.DecreaseStockRequest;
import com.timesale.common.proto.product.IncreaseStockRequest;
import com.timesale.common.proto.product.ProductServiceGrpc;
import com.timesale.order.application.port.ProductClient;
import com.timesale.order.infrastructure.exception.ExternalErrorCode;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductGrpcClient implements ProductClient {

    @GrpcClient("product-server")
    private ProductServiceGrpc.ProductServiceBlockingStub productGrpcStub;

    @Override
    public void decreaseStock(Long productId, Integer quantity) {
        try {
            DecreaseStockRequest request = DecreaseStockRequest.newBuilder()
                .setProductId(productId)
                .setQuantity(quantity)
                .build();

            productGrpcStub.decreaseStock(request);
        } catch (StatusRuntimeException e) {
            log.error("gRPC Product 서버 재고 차감 요청 실패: status={}, description={}",
                e.getStatus().getCode(), e.getStatus().getDescription());

            String description = e.getStatus().getDescription();

            ExternalErrorCode errorCode = ExternalErrorCode.findByCode(description)
                .orElse(ExternalErrorCode.EXTERNAL_SERVER_ERROR);
            throw new BusinessException(errorCode);
        }
    }

    @Override
    public void increaseStock(Long productId, Integer quantity) {
        try {
            IncreaseStockRequest request = IncreaseStockRequest.newBuilder()
                .setProductId(productId)
                .setQuantity(quantity)
                .build();

            productGrpcStub.increaseStock(request);

        } catch (StatusRuntimeException e) {
            log.error("gRPC Product 서버 재고 복구 요청 실패: status={}, description={}",
                e.getStatus().getCode(), e.getStatus().getDescription());

            String description = e.getStatus().getDescription();

            ExternalErrorCode errorCode = ExternalErrorCode.findByCode(description)
                .orElse(ExternalErrorCode.EXTERNAL_SERVER_ERROR);
            throw new BusinessException(errorCode);
        }
    }
}
