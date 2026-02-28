package com.timesale.order.api.grpc;

import com.timesale.common.exception.BusinessException;
import com.timesale.common.proto.order.GetOrderRequest;
import com.timesale.common.proto.order.GetOrderResponse;
import com.timesale.common.proto.order.OrderServiceGrpc;
import com.timesale.order.application.OrderService;
import com.timesale.order.domain.Order;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class OrderGrpcEndpoint extends OrderServiceGrpc.OrderServiceImplBase {

    private final OrderService orderService;

    @Override
    public void getOrder(GetOrderRequest request,
        StreamObserver<GetOrderResponse> responseObserver) {

        try {
            long orderId = request.getOrderId();
            Order order = orderService.getOrder(orderId);

            GetOrderResponse response = GetOrderResponse.newBuilder()
                .setOrderId(order.getId())
                .setStatus(order.getStatus().name())
                .setTotalPrice(order.getTotalPrice())
                .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (BusinessException e) {
            log.error("gRPC 주문 조회 실패 - 원인: {}", e.getMessage());
            responseObserver.onError(Status.FAILED_PRECONDITION
                .withDescription(e.getErrorCode().getCode())
                .asRuntimeException());
        } catch (Exception e) {
            log.error("gRPC 주문 조회 중 시스템 에러 발생", e);
            responseObserver.onError(Status.INTERNAL
                .withDescription("서버 내부 오류가 발생했습니다.")
                .asRuntimeException());
        }
    }
}
