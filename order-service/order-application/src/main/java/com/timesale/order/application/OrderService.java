package com.timesale.order.application;

import com.timesale.common.exception.BusinessException;
import com.timesale.order.application.dto.message.OrderFailMessage;
import com.timesale.order.application.port.MessageQueuePort;
import com.timesale.order.application.port.ProductClient;
import com.timesale.order.domain.Order;
import com.timesale.order.domain.OrderItem;
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
    private final MessageQueuePort messageQueue;

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
            rollbackStockPublish(productId, quantity);
            throw new BusinessException(OrderErrorCode.ORDER_SAVE_FAIL);
        }
    }

    @Transactional
    public void completeOrder(Long orderId) {
        getOrder(orderId).complete();
        log.info("주문 번호 [{}] 결제 완료 처리 성공", orderId);
    }

    @Transactional
    public void failOrder(Long orderId) {
        OrderItem orderItem = getOrderItemByOrderId(orderId);
        Order order = orderItem.getOrder();
        order.fail();
        rollbackStockPublish(orderItem.getProductId(), orderItem.getQuantity());
        log.info("주문 번호 [{}] 결제 실패 처리 성공", orderId);
    }

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new BusinessException(OrderErrorCode.NOT_FOUND));
    }

    private void rollbackStockPublish(Long productId, Integer quantity) {
        OrderFailMessage message = new OrderFailMessage(productId, quantity);
        messageQueue.publish("order-fail-events", message);
    }

    private OrderItem getOrderItemByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId)
            .orElseThrow(() -> new BusinessException(OrderErrorCode.NOT_FOUND));
    }
}
