package com.msa.order.model.response;

import com.msa.order.model.entity.Order;
import com.msa.order.model.entity.OrderDetail;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class OrderReadResponse {
    private Long orderId;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OrderReadResponse from(Order order) {
        OrderReadResponse orderCreateResponse = new OrderReadResponse();

        orderCreateResponse.orderId = order.getOrderId();
        orderCreateResponse.userId = order.getUserId();
        orderCreateResponse.createdAt = order.getCreatedAt();
        orderCreateResponse.updatedAt = order.getUpdatedAt();

        return orderCreateResponse;
    }
}
