package com.msa.outboxmessagerelay.util.event.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEventPayload implements EventPayload {
    private String idempotencyId;
    private String eventId;
    private Long productId;
    private Long orderedQty;
}
