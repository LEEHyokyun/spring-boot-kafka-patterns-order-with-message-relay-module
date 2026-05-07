package com.msa.order.service;

import com.msa.order.infra.kafka.event.Event;
import com.msa.order.infra.kafka.event.EventType;
import com.msa.order.infra.kafka.event.payload.OrderCreatedEventPayload;
import com.msa.order.infra.kafka.event.eventpublisher.KafkaEventPublisher;
import com.msa.order.model.entity.Order;
import com.msa.order.model.entity.OrderDetail;
import com.msa.order.model.request.OrderCreateRequest;
import com.msa.order.model.response.OrderListResponse;
import com.msa.order.model.response.OrderCreateResponse;
import com.msa.order.repository.OrderDetailRepository;
import com.msa.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final KafkaEventPublisher kafkaEventPublisher;

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

        kafkaEventPublisher.publish(
                Event.builder()
                        .eventTopic(EventType.Topic.SPRING_BOOT_KAFKA_PATTERNS_ORDER_PRODUCT)
                        .eventType(EventType.ORDER_CREATED)
                        .payload(
                                OrderCreatedEventPayload.builder()
                                        .productId(orderCreateRequest.getProductId())
                                        .orderedQty(orderCreateRequest.getOrderDetailQty())
                                        .build()
                        )
                        .build()
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
