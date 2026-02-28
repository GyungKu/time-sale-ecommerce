package com.timesale.order.application;

import com.timesale.common.exception.BusinessException;
import com.timesale.order.application.port.ProductClient;
import com.timesale.order.domain.Order;
import com.timesale.order.domain.OrderItem;
import com.timesale.order.domain.exception.OrderErrorCode;
import com.timesale.order.domain.port.OrderItemRepository;
import com.timesale.order.domain.port.OrderRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductClient productClient;

    @Transactional
    public Long placeOrder(Long userId, Long productId, Integer quantity, Integer orderPrice) {
        productClient.decreaseStock(productId, quantity);
        try {
            Integer totalPrice = orderPrice * quantity;
            Order order = Order.builder()
                .userId(userId)
                .totalPrice(totalPrice)
                .build();

            Order savedOrder = orderRepository.save(order);

            OrderItem orderItem = OrderItem.builder()
                .orderId(savedOrder.getId())
                .productId(productId)
                .quantity(quantity)
                .orderPrice(orderPrice)
                .build();

            orderItemRepository.save(orderItem);

            return savedOrder.getId();
        } catch (Exception e) {
            log.error("주문 DB 저장 중 에러 발생. 재고 복구 보상 트랜잭션을 시작합니다.", e);
            rollbackStock(productId, quantity);
            throw new BusinessException(OrderErrorCode.ORDER_SAVE_FAIL);
        }
    }

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new BusinessException(OrderErrorCode.NOT_FOUND));
    }

    private void rollbackStock(Long productId, Integer quantity) {
        try {
            productClient.increaseStock(productId, quantity);
        } catch (BusinessException compensationEx) {
            log.error("치명적 에러: 재고 복구 요청 실패!", compensationEx);
        }
    }

}
