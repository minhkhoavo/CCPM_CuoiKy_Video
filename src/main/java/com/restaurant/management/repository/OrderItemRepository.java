package com.restaurant.management.repository;

import com.restaurant.management.enums.OrderStatus;
import com.restaurant.management.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT o FROM OrderItem o WHERE"+
            " (:keyword IS NULL OR LOWER(o.dish.name) LIKE %:keyword% OR CAST(o.order.id AS STRING ) LIKE %:keyword%) AND "+
            "(:status IS NULL OR o.orderStatus = :status)")
    List<OrderItem> findByKeywordAndStatus(@Param("keyword") String keyword, @Param("status") OrderStatus status);

    //tinh số lượng dish trong tung ngay
    @Query("SELECT d.name AS dishName, SUM(oi.quantity) AS totalQuantity"+
            " FROM OrderItem oi JOIN oi.dish d "+
            " WHERE oi.orderStatus = 'COMPLETED' "+
            " GROUP BY d.name"+
            " ORDER BY totalQuantity")
    List<Object[]> getDishSalesStats();

    @Query("SELECT o.orderDate AS orderDate, " +
            "       (SELECT SUM(oi.quantity * oi.price) " +
            "        FROM OrderItem oi " +
            "        WHERE oi.order.id = o.id) AS totalCost, " +
            "       o.totalAmount AS totalRevenue " +
            "FROM Order o " +
            "WHERE o.orderDate BETWEEN :startDate AND :endDate " +
            "GROUP BY o.orderDate, o.id " +
            "ORDER BY o.orderDate ASC")
    List<Object[]> getCostAndRevenueByDateRange(@Param("startDate") LocalDateTime startDate,
                                                @Param("endDate") LocalDateTime endDate);

//    @Query("SELECT o.orderDate AS orderDate, " +
//            "       SUM(oi.quantity * oi.price) AS totalCost, " +
//            "       SUM(o.totalAmount) AS totalRevenue " +
//            "FROM Order o " +
//            "LEFT JOIN OrderItem oi ON oi.order.id = o.id " +
//            "WHERE o.orderDate BETWEEN :startDate AND :endDate " +
//            "GROUP BY o.orderDate " +
//            "ORDER BY o.orderDate ASC")
//    List<Object[]> getCostAndRevenueByDateRange(@Param("startDate") LocalDateTime startDate,
//                                                @Param("endDate") LocalDateTime endDate);

}
