package com.msa.outboxmessagerelay.infra.outbox.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@EnableAsync
@EnableScheduling
@Configuration
public class OutboxMessagePollingConfig {

    //비동기 스레드 풀 환경 구성 : Outbox 메시지 전송 후 미전송 내역에 대한 별도 전송
    //Single Thread
    @Bean
    public ThreadPoolTaskScheduler messageRelayPublishingWithPeriodicalPollingExecutor() {

        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(1); //single thread
        threadPoolTaskScheduler.setThreadNamePrefix("messageRelayPublishingWithPeriodicalPollingExecutor-");
        threadPoolTaskScheduler.initialize();

        return threadPoolTaskScheduler;
    }
}
