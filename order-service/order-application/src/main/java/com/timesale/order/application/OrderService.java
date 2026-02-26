package com.timesale.order.application;

import com.timesale.order.application.port.ProductClient;
import com.timesale.order.domain.Order;
import com.timesale.order.domain.OrderItem;
import com.timesale.order.domain.OrderStatus;
import com.timesale.order.domain.port.OrderItemRepository;
import com.timesale.order.domain.port.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductClient productClient;

    @Transactional
    public Long placeOrder(Long userId, Long productId, Integer quantity, Integer orderPrice) {
        productClient.decreaseStock(productId, quantity);

        int totalPrice = orderPrice * quantity;
        Order order = Order.builder()
            .userId(userId)
            .status(OrderStatus.COMPLETED)
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
    }

}
