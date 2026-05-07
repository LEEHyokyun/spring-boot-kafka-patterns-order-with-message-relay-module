package com.msa.outboxmessagerelay.infra.outbox.handler;

import com.msa.outboxmessagerelay.infra.outbox.model.OutboxEvent;
import com.msa.outboxmessagerelay.model.entity.Outbox;
import com.msa.outboxmessagerelay.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxMessageRelayHandler {

    /*
    * publish Event 동작 전/후로 발생하는 동작들
    * */

    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, Object> outboxMessageRelayKafkaTemplate;

    /*
    * 트랜잭션 전에 outbox에 페이로드를 보관한다.
    * */
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void createOutbox(OutboxEvent outboxEvent){
        log.info("[OutboxMessageRelayHandler.createOutbox][INFO] outboxEvent = {}", outboxEvent);
        outboxRepository.save(outboxEvent.getOutbox());
    }

    /*
    * 트랜잭션 이후에는 카프카 이벤트를 전송한다.
    * */
    @Async("messageRelayPublishingAfterTxExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishOutboxEvent(OutboxEvent outboxEvent){
        this.publishOutboxEvent(outboxEvent.getOutbox());
    }

    private void publishOutboxEvent(Outbox outbox){
        log.info("[OutboxMessageRelayHandler.publishOutboxEvent][INFO] outbox = {}", outbox);
        try {
            //outbox 메시지를 전송했으면
            outboxMessageRelayKafkaTemplate.send(
                    outbox.getEventTopic(),
                    outbox.getPayload() //payload = Event
            ).get(1, TimeUnit.SECONDS);

            //outbox 메시지를 삭제한다.
            outboxRepository.delete(outbox);
        } catch (Exception e) {
            log.info("[OutboxMessageRelayHandler.publishOutboxEvent][ERROR] outbox = {}", outbox, e);
        }
    }

    /*
    * 미전송 내역에 대한 별도 전송
    * */
    @Scheduled(
            fixedDelay = 10, //10sec 주기로
            initialDelay = 5, //최초 실행 후 5초 delay 발생
            timeUnit = TimeUnit.SECONDS,
            scheduler = "messageRelayPublishingWithPeriodicalPollingExecutor"
    )
    public void publishPollingAndPendingEvent(){

        log.info("[OutboxMessageRelayHandler.publishPollingAndPendingEvent][INFO] now = {}", LocalDateTime.now());

        List<Outbox> list = outboxRepository.findAllByCreatedAtLessThanEqualOrderByCreatedAtAsc(
                LocalDateTime.now().minusSeconds(10) //생성된지 10초 지난 이벤트들
        );

        for(Outbox outbox : list){

            log.info("outbox = {}", outbox);

            this.publishOutboxEvent(outbox);
        }
    }
}
