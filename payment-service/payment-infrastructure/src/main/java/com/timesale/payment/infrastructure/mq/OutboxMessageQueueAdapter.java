package com.timesale.payment.infrastructure.mq;

import com.timesale.common.outbox.OutboxAggregate;
import com.timesale.common.outbox.OutboxService;
import com.timesale.payment.application.port.MessageQueuePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxMessageQueueAdapter implements MessageQueuePort {

    private final OutboxService outboxService;

    @Override
    public <T> void publish(String topic, T message) {
        outboxService.save(OutboxAggregate.PAYMENT, topic, message);
    }

}
