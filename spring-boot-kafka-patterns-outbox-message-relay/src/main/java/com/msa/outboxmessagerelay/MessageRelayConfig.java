package com.msa.outboxmessagerelay;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/*
* for not WAS,
* for modules(Configuration)
* */
/*
* 비동기 처리 -> 트랜잭션 레벨에서 after commit 시점에서 kafka로 메시지 전송
* 스케쥴링 처리 ->  미전송 내역에 대해 Kafka로 별도 전송
* */
//@EnableAsync
//@Configuration
@ComponentScan("com.msa.outboxmessagerelay")
//@EnableScheduling
public class MessageRelayConfig {

    /*
    * 도메인 / 책임 별 분리
    * */

//    @Value("${spring.kafka.bootstrap-servers}")
//    private String bootStrapServers;
//
//    @Value("${spring.kafka.producer.value-serializer}")
//    private String valueSerializer;
//
//    @Value("${spring.kafka.producer.key-serializer}")
//    private String keySerializer;
//
//    @Bean
//    public KafkaTemplate<String, Object> kafkaTemplate() {
//        Map<String, Object> configProps = new HashMap<>();
//
//        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
//        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
//        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
//        configProps.put(ProducerConfig.ACKS_CONFIG, "all"); //leader + replica까지 메시지 발행 완료 시 성공
//
//        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(configProps));
//    }
//
//    //비동기 스레드 풀 환경 구성 : 트랜잭션 완료 후 Outbox 메시지 전송
//    @Bean
//    public Executor messageRelayPublishjingAfterTxExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//
//        executor.setCorePoolSize(20);
//        executor.setMaxPoolSize(50);
//        executor.setQueueCapacity(100);
//        executor.setThreadNamePrefix("SPRING-BOOT-KAFKA-PATTERNS-OUTBOX-MESSAGE-RELAY");
//        return executor;
//    }

//    //비동기 스레드 풀 환경 구성 : Outbox 메시지 전송 후 미전송 내역에 대한 별도 전송
//    //Single Thread
//    @Bean
//    public Executor messageRelayPublishjingWithPeriodicalPollingExecutor() {
//        return Executors.newSingleThreadExecutor();
//    }
}
