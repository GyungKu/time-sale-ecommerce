package com.timesale.common.outbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OutboxService {

    private final OutboxJpaRepository outboxJpaRepository;
    private final ObjectMapper objectMapper;

    public OutboxService(OutboxJpaRepository outboxJpaRepository) {
        this.outboxJpaRepository = outboxJpaRepository;
        this.objectMapper = new ObjectMapper();
    }

    public <T> void save(OutboxAggregate aggregate, String topic, T message) {
        try {
            String payload = objectMapper.writeValueAsString(message);
            outboxJpaRepository.save(Outbox.create(aggregate, topic, payload));
            log.info("outbox 생성 성공 - topic: {}, payload: {}", topic, payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Outbox 메시지 직렬화 실패", e);
        }
    }

}
