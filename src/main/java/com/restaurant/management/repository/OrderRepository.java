package com.restaurant.management.repository;

import com.restaurant.management.enums.OrderStatus;
import com.restaurant.management.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Optional<Order> findFirstByDiningTableIdAndAndOrderStatusIn(Long diningTableId, List<OrderStatus> statuses);

    @Query("SELECT DATE(o.orderDate) AS date, COUNT(o) AS count "+
            "FROM Order o "+
            "GROUP BY DATE(o.orderDate)" +
            "ORDER BY DATE(o.orderDate)")
    List<Object[]> countOrdersByOrderDay();
}