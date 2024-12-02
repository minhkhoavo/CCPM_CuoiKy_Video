package com.restaurant.management.service;

import com.restaurant.management.enums.OrderStatus;
import com.restaurant.management.model.Order;
import com.restaurant.management.model.OrderItem;
import com.restaurant.management.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderItem> getAllOrderItem(){
        return orderItemRepository.findAll();
    }

    public OrderItem getOrderItemById(Long id){
        return orderItemRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("OrderItem Not Found"));
    }
    public void updateOrderStatus(Long orderItemID, OrderStatus orderStatus){
        OrderItem orderItem = orderItemRepository.findById(orderItemID)
                .orElseThrow(() -> new RuntimeException("Order item not found"));
        orderItem.setOrderStatus(orderStatus);
        orderItemRepository.save(orderItem);
    }

    public List<OrderItem> searchOrderItems(String keyword, String statusFilter) {
        //chuan hoa keyword
        String searchKeyword = (keyword != null && !keyword.trim().isEmpty()) ? keyword.trim().toLowerCase() : null;
        //chuan hoa status
        OrderStatus status = (statusFilter != null && !statusFilter.isEmpty()) ? OrderStatus.valueOf(statusFilter) : null;
        return orderItemRepository.findByKeywordAndStatus(searchKeyword, status);
    }
}
