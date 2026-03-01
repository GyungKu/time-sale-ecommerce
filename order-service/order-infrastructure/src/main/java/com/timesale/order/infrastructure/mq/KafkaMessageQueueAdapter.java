package com.timesale.order.infrastructure.mq;

import com.timesale.order.application.port.MessageQueuePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessageQueueAdapter implements MessageQueuePort {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public <T> void publish(String topic, T message) {
        kafkaTemplate.send(topic, message)
            .whenComplete((result, ex) -> {
                if (ex != null) {
                    log.error("Kafka publish failed. topic: {}, message: {}", topic, message, ex);
                }
            });
    }
}
