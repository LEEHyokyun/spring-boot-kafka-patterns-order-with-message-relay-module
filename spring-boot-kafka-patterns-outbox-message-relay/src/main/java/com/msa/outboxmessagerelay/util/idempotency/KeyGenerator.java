package com.msa.outboxmessagerelay.util.idempotency;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KeyGenerator {

    public static String generateEventId(){
        return Long.toHexString(System.nanoTime());
    }
}
