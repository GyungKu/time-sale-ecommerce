package com.timesale.order.application;

import com.timesale.order.application.dto.message.OrderFailMessage;
import com.timesale.order.application.port.MessageQueuePort;
import com.timesale.order.domain.Order;
import com.timesale.order.domain.port.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderFailHandler {

    private final OrderRepository orderRepository;
    private final MessageQueuePort messageQueue;

    @Transactional
    public void failOrder(Order order, Long productId, Integer quantity) {
        orderRepository.save(order);
        rollbackStockPublish(productId, quantity);
        log.info("주문 번호 [{}] 결제 실패 처리 성공", order.getId());
    }

    @Transactional
    public void rollbackStockPublish(Long productId, Integer quantity) {
        OrderFailMessage message = new OrderFailMessage(productId, quantity);
        messageQueue.publish("order-fail-events", message);
    }
}
