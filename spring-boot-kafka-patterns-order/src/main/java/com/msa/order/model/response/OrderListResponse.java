package com.msa.order.model.response;

import com.msa.order.model.entity.Order;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class OrderListResponse {

    private List<OrderReadResponse> list;

    public static OrderListResponse fromResponse(List<OrderReadResponse> orders){

        OrderListResponse orderListResponse = new OrderListResponse();
        orderListResponse.list = orders;

        return orderListResponse;
    }

    /*
    * to be used
    * */
    public static OrderListResponse from(List<Order> orders){
        return fromResponse(orders.stream().map(OrderReadResponse::from).toList());
    }
}
