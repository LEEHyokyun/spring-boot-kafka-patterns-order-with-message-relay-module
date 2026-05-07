package com.msa.order.model.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OrderCreateRequest {
    private Long productId;
    private Long orderDetailQty;
    private Long orderDetailPrice;
}
