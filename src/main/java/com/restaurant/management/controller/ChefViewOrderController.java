package com.restaurant.management.controller;

import com.restaurant.management.enums.OrderStatus;
import com.restaurant.management.model.OrderItem;
import com.restaurant.management.service.DishService;
import com.restaurant.management.service.OrderItemService;
import com.restaurant.management.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/order-item")
public class ChefViewOrderController {
    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private DishService dishService;

    @GetMapping
    public String listOrder(Model model) {
        List<OrderItem> orderItems = orderItemService.getAllOrderItem();
        model.addAttribute("orderItems", orderItems);
        return "pages/ChefViewOrder/view-order";
    }

    @PostMapping("/updateOrderStatus")
    public String updateOrderStatus(@RequestParam Long orderItemId, @RequestParam Long dishId, @RequestParam OrderStatus orderStatus, Model model ) throws IllegalAccessException {
        //cập nhật lại kho hàng
        if(orderStatus.equals(OrderStatus.COMPLETED)) {
            dishService.processDish(dishId);
        }
        // Cập nhật trạng thái của order item
        orderItemService.updateOrderStatus(orderItemId, orderStatus);
        return "redirect:/order-item";
    }
}
