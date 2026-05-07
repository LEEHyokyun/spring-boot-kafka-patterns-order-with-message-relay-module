package com.msa.outboxmessagerelay.infra.event;

import com.msa.outboxmessagerelay.infra.event.payload.EventPayload;
import com.msa.outboxmessagerelay.model.entity.Outbox;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutboxEvent {

    /*
    * outbox 그 자체를 전송
    * */

    private Outbox outbox;
    private Event event;

    public static OutboxEvent of(Outbox outbox, Event event){
        OutboxEvent outboxEvent = new OutboxEvent();

        outboxEvent.outbox = outbox;
        outboxEvent.event = event;

        return outboxEvent;
    }
}
