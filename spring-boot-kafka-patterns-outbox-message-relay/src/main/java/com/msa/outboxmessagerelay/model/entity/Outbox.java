package com.msa.outboxmessagerelay.model.entity;

import com.msa.outboxmessagerelay.infra.event.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "outbox")
@NoArgsConstructor
@AllArgsConstructor
public class Outbox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long outboxId;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private String eventTopic;

    private String payload;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Outbox create(EventType eventType, String eventTopic, String payload) {

        Outbox outbox = new Outbox();

        outbox.eventType = eventType;
        outbox.eventTopic = eventTopic;
        outbox.payload = payload;
        outbox.createdAt = LocalDateTime.now();
        outbox.updatedAt = outbox.createdAt;

        return outbox;
    }

}
