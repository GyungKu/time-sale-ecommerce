package com.timesale.order.api.mq;

import com.timesale.order.api.dto.message.PaymentResultMessage;
import com.timesale.order.application.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventConsumer {

    private final OrderService orderService;

    @KafkaListener(topics = "payment-events")
    public void consumePaymentEvent(PaymentResultMessage message) {
        if (message.status().equals("SUCCESS")) {
            log.info("Kafka 결제 성공 이벤트 수신: {}", message.orderId());
            orderService.completeOrder(message.orderId());
        }

        if (message.status().equals("FAIL")) {
            log.info("Kafka 결제 실패 이벤트 수신: {}", message.orderId());
            orderService.failOrder(message.orderId());
        }
    }

}
