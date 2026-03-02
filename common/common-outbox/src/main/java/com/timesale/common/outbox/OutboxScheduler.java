package com.timesale.common.outbox;

import com.timesale.common.kafka.KafkaPublisher;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxScheduler {

    private final OutboxJpaRepository outboxJpaRepository;
    private final KafkaPublisher kafkaPublisher;

    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void publishEvents() {
        List<Outbox> events = outboxJpaRepository.findTop100ByPublishedFalseOrderByIdAsc();

        if (events.isEmpty()) return;

        log.info("메시지 발행 - payload: {}", events.get(0).getPayload());
        events.forEach(outbox -> {
            try {
                kafkaPublisher.send(outbox.getTopic(), outbox.getPayload());
                outbox.markAsPublished();

                log.info("Outbox 메시지 발행 성공 - id: {}, topic: {}",
                    outbox.getId(), outbox.getTopic());
            } catch (Exception e) {
                log.error("Outbox 메시지 발행 실패 - id: {}", outbox.getId(), e);
            }
        });
        outboxJpaRepository.saveAll(events);
    }

}
