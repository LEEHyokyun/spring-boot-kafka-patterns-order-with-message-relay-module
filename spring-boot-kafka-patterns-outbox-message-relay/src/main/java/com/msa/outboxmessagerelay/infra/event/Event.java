package com.msa.outboxmessagerelay.infra.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.outboxmessagerelay.util.event.payload.EventPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event<T extends EventPayload> {
    private String eventTopic;
    private EventType eventType;
    private T payload;


    public static Event of(String eventTopic, EventType eventType, EventPayload eventPayload) {
        Event event = new Event();

        event.eventTopic = eventTopic;
        event.eventType = eventType;
        event.payload = eventPayload;

        return event;
    }

    public String toJson() {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Event toJson 실패", e);
        }
    }

}
