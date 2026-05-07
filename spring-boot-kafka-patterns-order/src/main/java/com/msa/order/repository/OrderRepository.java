package com.msa.order.repository;

import com.msa.order.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

//    @Query(
//            value = " select o.order_id, o.product_id, o.user_id, o.order_qty, o.order_unit_price, o.order_total_price, o.created_at, o.updated_at " +
//                    " from orders o" +
//                    " where o.user_id = :userId",
//            nativeQuery = true
//    )
    List<Order> findByUserId(
            @Param("userId") Long userId
    );
}
