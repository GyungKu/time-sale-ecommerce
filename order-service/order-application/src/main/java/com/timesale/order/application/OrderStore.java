package com.timesale.order.application;

import com.timesale.order.domain.Order;
import com.timesale.order.domain.OrderItem;
import com.timesale.order.domain.port.OrderItemRepository;
import com.timesale.order.domain.port.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderStore {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public Long saveOrder(Order order, Long productId, Integer quantity, Integer orderPrice) {
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
