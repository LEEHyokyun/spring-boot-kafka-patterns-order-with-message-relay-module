package com.msa.order.infra.kafka.event;

import com.msa.order.infra.kafka.event.payload.EventPayload;
import com.msa.order.infra.kafka.event.payload.OrderCreatedEventPayload;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum EventType {
    ORDER_CREATED(OrderCreatedEventPayload.class, Topic.SPRING_BOOT_KAFKA_PATTERNS_ORDER_PRODUCT)
    ;

    private final Class<? extends EventPayload> payloadClass;
    private final String topic;

    public static EventType from(String type){
        try {
            return valueOf(type);
        } catch (Exception e) {
            log.error("[EventType.from] type={}", type, e);
            return null;
        }
    }

    public static class Topic {
        public static final String SPRING_BOOT_KAFKA_PATTERNS_ORDER_PRODUCT = "ORDER.CREATED";
    }
}
