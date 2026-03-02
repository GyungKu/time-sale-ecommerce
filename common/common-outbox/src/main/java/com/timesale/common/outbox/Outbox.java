package com.timesale.common.outbox;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "outbox_events")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Outbox {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OutboxAggregate aggregate;

    private String topic;

    @Column(columnDefinition = "TEXT")
    private String payload;

    private Boolean published;

    private LocalDateTime createdAt;

    public static Outbox create(OutboxAggregate aggregate, String topic, String payload) {
        Outbox outbox = new Outbox();
        outbox.aggregate = aggregate;
        outbox.topic = topic;
        outbox.payload = payload;
        outbox.published = false;
        outbox.createdAt = LocalDateTime.now();
        return outbox;
    }

    public void markAsPublished() {
        published = true;
    }

}
