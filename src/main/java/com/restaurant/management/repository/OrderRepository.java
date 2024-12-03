package com.restaurant.management.repository;

import com.restaurant.management.enums.OrderStatus;
import com.restaurant.management.model.Order;
import com.restaurant.management.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Optional<Order> findFirstByDiningTableIdAndAndOrderStatusIn(Long diningTableId, List<OrderStatus> statuses);
    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.id = :orderId")
    List<OrderItem> findOrderDetailByOrderId(@Param("orderId") String orderId);

}