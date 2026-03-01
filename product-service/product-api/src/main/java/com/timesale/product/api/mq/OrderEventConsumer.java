package com.timesale.product.api.mq;

import com.timesale.product.api.dto.message.OrderFailMessage;
import com.timesale.product.application.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final ProductService productService;

    @KafkaListener(topics = "order-fail-events")
    public void consumePaymentEvent(OrderFailMessage message) {
        log.info("Kafka 주문 실패 이벤트 수신: {}", message.productId());
        productService.increaseProductStock(message.productId(), message.quantity());
    }

}
