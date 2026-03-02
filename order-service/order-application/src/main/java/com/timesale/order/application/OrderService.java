package com.timesale.order.application;

import com.timesale.common.exception.BusinessException;
import com.timesale.order.application.port.ProductClient;
import com.timesale.order.domain.Order;
import com.timesale.order.domain.OrderItem;
import com.timesale.order.domain.OrderStatus;
import com.timesale.order.domain.exception.OrderErrorCode;
import com.timesale.order.domain.port.OrderItemRepository;
import com.timesale.order.domain.port.OrderRepository;
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
    private final OrderFailHandler orderFailHandler;
    private final OrderStore orderStore;


    public Long placeOrder(Long userId, Long productId, Integer quantity, Integer orderPrice) {
        productClient.decreaseStock(productId, quantity);
        try {
            Integer totalPrice = orderPrice * quantity;
            Order order = Order.builder()
                .userId(userId)
                .totalPrice(totalPrice)
                .build();

            return orderStore.saveOrder(order, productId, quantity, orderPrice);
        } catch (Exception e) {
            log.error("주문 DB 저장 중 에러 발생. 재고 복구 보상 트랜잭션을 시작합니다.", e);
            orderFailHandler.rollbackStockPublish(productId, quantity);
            throw new BusinessException(OrderErrorCode.ORDER_SAVE_FAIL);
        }
    }

    @Transactional
    public void completeOrder(Long orderId) {
        Order order = getOrderByOrderId(orderId);
        if (order.isAlreadyOrder()) {
            log.info("이미 처리된 주문입니다. - orderId: {}", orderId);
            return;
        }
        order.complete();
        log.info("주문 번호 [{}] 결제 완료 처리 성공", orderId);
    }

    public void failOrder(Long orderId) {
        Order order = getOrderByOrderId(orderId);
        if (order.isAlreadyOrder()) {
            log.info("이미 처리된 주문입니다. - orderId: {}", orderId);
            return;
        }
        OrderItem orderItem = getOrderItemByOrderId(orderId);
        order.fail();
        orderFailHandler.failOrder(order, orderItem.getProductId(), orderItem.getQuantity());
    }

    @Transactional(readOnly = true)
    public Order getOrderByOrderId(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new BusinessException(OrderErrorCode.NOT_FOUND));
    }

    private OrderItem getOrderItemByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId)
            .orElseThrow(() -> new BusinessException(OrderErrorCode.NOT_FOUND));
    }
}