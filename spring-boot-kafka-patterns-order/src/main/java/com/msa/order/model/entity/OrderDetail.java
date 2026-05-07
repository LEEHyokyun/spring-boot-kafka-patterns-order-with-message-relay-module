package com.msa.order.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "orders_detail")
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;

    /*
    * 연관관계의 주인
    * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Long productId;

    private Long orderDetailPrice;
    private Long orderDetailQty;
    private Long orderTotalPrice;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OrderDetail create(Long productId, Long orderDetailQty, Long orderDetailPrice){

        OrderDetail order = new OrderDetail();

        order.productId = productId;
        order.orderDetailPrice = orderDetailPrice;
        order.orderDetailQty = orderDetailQty;
        order.orderTotalPrice = orderDetailPrice * orderDetailQty;
        order.createdAt = LocalDateTime.now();
        order.updatedAt = order.createdAt;

        return order;
    }

    /*
    * setter
    * = 본 엔티티는 자식이고, 부모 엔티티를 먼저 필요로 합니다.
    * */
    public void setOrder(Order order) {
        this.order = order;
    }
}
