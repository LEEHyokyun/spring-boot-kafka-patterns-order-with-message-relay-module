package com.msa.order.model.response;

import com.msa.order.model.entity.Order;
import com.msa.order.model.entity.OrderDetail;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class OrderCreateResponse {
    private Long orderId;
    private Long userId;
    private Long productId;
    private Long orderDetailQty;
    private Long orderDetailPrice;
    private Long orderTotalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OrderCreateResponse from(Order order, OrderDetail orderDetail) {
        OrderCreateResponse orderCreateResponse = new OrderCreateResponse();

        orderCreateResponse.orderId = order.getOrderId();
        orderCreateResponse.userId = order.getUserId();
        orderCreateResponse.productId = orderDetail.getProductId();
        orderCreateResponse.orderDetailQty = orderDetail.getOrderDetailQty();
        orderCreateResponse.orderDetailPrice = orderDetail.getOrderDetailPrice();
        orderCreateResponse.orderTotalPrice = orderDetail.getOrderTotalPrice();
        orderCreateResponse.createdAt = order.getCreatedAt();
        orderCreateResponse.updatedAt = order.getUpdatedAt();

        return orderCreateResponse;
    }
}
