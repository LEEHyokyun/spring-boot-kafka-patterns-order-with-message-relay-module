package com.msa.order.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private Long userId;

    //private Long productId;
    //private Long orderQty;
    //private Long orderUnitPrice;
    //private Long orderTotalPrice;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /*
    * 조회용
    * */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public static Order create(Long userId){

        Order order = new Order();

        order.userId = userId;
        //order.orderQty = orderQty;
        //order.orderUnitPrice = orderUnitPrice;
        //order.orderTotalPrice = orderUnitPrice * orderQty;
        order.createdAt = LocalDateTime.now();
        order.updatedAt = order.createdAt;

        return order;
    }

    /*
    * 연관관계 주인(자식)의 방향성..
    * 반드시 부모 엔티티인 Order를 먼저 생성 및 반영하고, 그 다음에 detail 측에서 이를 참조하도록 요함.
    * */
    public void addDetail(OrderDetail orderDetail){
        this.orderDetails.add(orderDetail);
        /*
        * key point : Entity graph 생성.
        * */
        orderDetail.setOrder(this);
    }
}
