package com.timesale.product.api.grpc;

import com.timesale.common.proto.product.DecreaseStockRequest;
import com.timesale.common.proto.product.DecreaseStockResponse;
import com.timesale.common.proto.product.IncreaseStockRequest;
import com.timesale.common.proto.product.IncreaseStockResponse;
import com.timesale.common.proto.product.ProductServiceGrpc;
import com.timesale.product.application.ProductService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class ProductGrpcEndpoint extends ProductServiceGrpc.ProductServiceImplBase {

    private final ProductService productService;

    @Override
    public void decreaseStock(DecreaseStockRequest request,
        StreamObserver<DecreaseStockResponse> responseObserver) {

        try {
            long productId = request.getProductId();
            int quantity = request.getQuantity();

            log.info("gRPC 재고 차감 요청 수신 - productId: {}, quantity: {}", productId, quantity);

            productService.decreaseProductStock(productId, quantity);

            DecreaseStockResponse response = DecreaseStockResponse.newBuilder()
                .setSuccess(true)
                .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("gRPC 재고 차감 실패", e);
            responseObserver.onError(Status.INTERNAL
                .withDescription(e.getMessage())
                .withCause(e)
                .asRuntimeException());
        }
    }

    @Override
    public void increaseStock(IncreaseStockRequest request,
        StreamObserver<IncreaseStockResponse> responseObserver) {

        try {
            long productId = request.getProductId();
            int quantity = request.getQuantity();

            log.info("gRPC 재고 복구 요청 수신 - productId: {}, quantity: {}", productId, quantity);

            productService.decreaseProductStock(productId, quantity);

            IncreaseStockResponse response = IncreaseStockResponse.newBuilder()
                .setSuccess(true)
                .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("gRPC 재고 차감 실패", e);
            responseObserver.onError(Status.INTERNAL
                .withDescription(e.getMessage())
                .withCause(e)
                .asRuntimeException());
        }
    }
}
