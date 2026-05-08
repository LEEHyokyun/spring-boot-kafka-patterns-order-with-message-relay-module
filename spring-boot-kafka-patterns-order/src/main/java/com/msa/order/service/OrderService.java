package com.msa.order.service;

import com.msa.order.infra.kafka.event.EventType;
import com.msa.order.util.kafkaEvent.KafkaEventPublisher;
import com.msa.order.model.entity.Order;
import com.msa.order.model.entity.OrderDetail;
import com.msa.order.model.request.OrderCreateRequest;
import com.msa.order.model.response.OrderListResponse;
import com.msa.order.model.response.OrderCreateResponse;
import com.msa.order.repository.OrderDetailRepository;
import com.msa.order.repository.OrderRepository;
import com.msa.outboxmessagerelay.infra.event.Event;
import com.msa.outboxmessagerelay.publisher.OutboxMessageRelayPublisher;
import com.msa.outboxmessagerelay.util.event.payload.OrderCreatedEventPayload;
import com.msa.outboxmessagerelay.util.idempotency.KeyGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.msa.outboxmessagerelay.infra.event.EventType.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final KafkaEventPublisher kafkaEventPublisher;
    private final OutboxMessageRelayPublisher outboxMessageRelayPublisher;

    @Transactional
    public OrderCreateResponse create(Long userId, OrderCreateRequest orderCreateRequest) {

        Order order = Order.create(
                userId
        );

        OrderDetail orderDetail = OrderDetail.create(
                orderCreateRequest.getProductId(),
                orderCreateRequest.getOrderDetailQty(),
                orderCreateRequest.getOrderDetailPrice()
        );

        order.addDetail(orderDetail);
        orderRepository.save(order);

        /*
         * 중요 : [ASIS] 바로 이벤트를 발생하지 않고,
         * */
//        kafkaEventPublisher.publish(
//                Event.builder()
//                        .eventTopic(EventType.Topic.SPRING_BOOT_KAFKA_PATTERNS_ORDER_PRODUCT)
//                        .eventType(EventType.ORDER_CREATED)
//                        .payload(
//                                OrderCreatedEventPayload.builder()
//                                        .productId(orderCreateRequest.getProductId())
//                                        .orderedQty(orderCreateRequest.getOrderDetailQty())
//                                        .build()
//                        )
//                        .build()
//        );

        /*
         * 중요 : 바로 이벤트를 발생하지 않고,
         * KEY POINTS : tx 커밋전에 일단 outbox에 저장하여 메시지 유실 방지 장치 적용.
         * */
        outboxMessageRelayPublisher.publish(
                Topic.SPRING_BOOT_KAFKA_PATTERNS_ORDER_PRODUCT,
                ORDER_CREATED,
                Event.builder()
                        .eventTopic(EventType.Topic.SPRING_BOOT_KAFKA_PATTERNS_ORDER_PRODUCT)
                        .eventType(ORDER_CREATED)
                        .payload(
                                OrderCreatedEventPayload.builder()
                                        .idempotencyId(order.getOrderId().toString())
                                        //.idempotencyId("1")
                                        .eventId(KeyGenerator.generateEventId())
                                        .productId(orderCreateRequest.getProductId())
                                        .orderedQty(orderCreateRequest.getOrderDetailQty())
                                        .build()
                        )
                        .build()
                        .getPayload()
        );

        /*
        * created order
        * */
        return OrderCreateResponse.from(order, orderDetail);
    }

    public OrderListResponse readOrderOfUser(Long userId) {
        return OrderListResponse.from(
                orderRepository.findByUserId(userId)
        );
    }

    public OrderListResponse readAllOrders() {
        return OrderListResponse.from(
                orderRepository.findAll()
        );
    }

}
