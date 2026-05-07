package com.msa.outboxmessagerelay.publisher;

import com.msa.outboxmessagerelay.infra.event.Event;
import com.msa.outboxmessagerelay.infra.event.EventType;
import com.msa.outboxmessagerelay.infra.event.OutboxEvent;
import com.msa.outboxmessagerelay.infra.event.payload.EventPayload;
import com.msa.outboxmessagerelay.model.entity.Outbox;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxMessageRelayPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(String eventTopic, EventType eventType, EventPayload eventPayload) {
        Event event = Event.of(
                eventTopic,
                eventType,
                eventPayload
        );

        Outbox outbox = Outbox.create(
                eventType,
                eventTopic,
                event.toJson()
        );

        /*
        * application level(tx)에서 이벤트를 유발하는 행위
        * event 동작 전/후로 outbox 이벤트를 동작한다.
        * */
        applicationEventPublisher.publishEvent(OutboxEvent.of(outbox, event));
    }

}
