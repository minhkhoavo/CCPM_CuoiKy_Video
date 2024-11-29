package com.restaurant.management.service;

import com.restaurant.management.enums.OrderStatus;
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

    public void updateOrderStatus(Long orderItemID, OrderStatus orderStatus){
        OrderItem orderItem = orderItemRepository.findById(orderItemID)
                .orElseThrow(() -> new RuntimeException("Order item not found"));
        orderItem.setOrderStatus(orderStatus);
        orderItemRepository.save(orderItem);
    }

}
