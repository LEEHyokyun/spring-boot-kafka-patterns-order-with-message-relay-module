package com.msa.order.controller;

import com.msa.order.model.request.OrderCreateRequest;
import com.msa.order.model.response.OrderListResponse;
import com.msa.order.model.response.OrderCreateResponse;
import com.msa.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/*
* model 효율화
* - create, update 세부 목적 등으로 Request Object 운용(처리과정 및 세부 로직 등으로 인해 필요 항목이 다를 수 있음).
* - request Object -> controller -> User Entity(domain 규칙을 포함한 엔티티 create) -> service -> Response (from domain entity)
* - Response Object는 처리 시 활용한 객체 상태/값들을 보여주는 목적으로 분리하지 않고 일괄 운용
* */
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/health-check")
    public String status(){
        return String.format("Now Working");
    }

    @GetMapping("/orders")
    public OrderListResponse readAllOrders(){

        return orderService.readAllOrders();
    }

//    @Observed(
//            name = "read.order.orders",
//            contextualName = "[READ OREDER OF ONE USER]",
//            lowCardinalityKeyValues = {
//                    "SERVICE", "ORDER1-SERVICE",
//                    "OPERATION", "READ"
//            }
//    )
    @GetMapping("/orders/{userId}")
    public OrderListResponse readOrderOfUser(@PathVariable("userId") Long userId){

        return orderService.readOrderOfUser(userId);
    }

    @PostMapping("/orders/{userId}")
    public OrderCreateResponse create(
            @PathVariable("userId") Long userId,
            @RequestBody OrderCreateRequest request
        ){

        return orderService.create(userId, request);
    }

}
