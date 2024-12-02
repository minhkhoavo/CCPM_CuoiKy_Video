package com.restaurant.management.repository;

import com.restaurant.management.enums.OrderStatus;
import com.restaurant.management.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT o FROM OrderItem o WHERE"+
            " (:keyword IS NULL OR LOWER(o.dish.name) LIKE %:keyword% OR CAST(o.order.id AS STRING ) LIKE %:keyword%) AND "+
            "(:status IS NULL OR o.orderStatus = :status)")
    List<OrderItem> findByKeywordAndStatus(@Param("keyword") String keyword, @Param("status") OrderStatus status);
}
