package com.timesale.payment.infrastructure.client;

import com.timesale.common.exception.BusinessException;
import com.timesale.common.proto.order.GetOrderRequest;
import com.timesale.common.proto.order.OrderServiceGrpc;
import com.timesale.payment.application.port.OrderClient;
import com.timesale.payment.infrastructure.exception.OrderExternalErrorCode;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderClientImpl implements OrderClient {

    @GrpcClient("order-server")
    private OrderServiceGrpc.OrderServiceBlockingStub orderGrpcStub;

    @Override
    public Integer getOrder(Long orderId) {
        try {
            GetOrderRequest request = GetOrderRequest.newBuilder()
                .setOrderId(orderId)
                .build();

            return orderGrpcStub.getOrder(request).getTotalPrice();
        } catch (StatusRuntimeException e) {
            log.error("gRPC Order 서버 조회 실패: status={}, description={}",
                e.getStatus().getCode(), e.getStatus().getDescription());

            String description = e.getStatus().getDescription();

            OrderExternalErrorCode errorCode = OrderExternalErrorCode.findByCode(description)
                .orElse(OrderExternalErrorCode.EXTERNAL_SERVER_ERROR);
            throw new BusinessException(errorCode);
        }
    }
}
