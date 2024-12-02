package com.restaurant.management.controller;

import com.restaurant.management.model.Dish;
import com.restaurant.management.model.Order;
import com.restaurant.management.model.OrderItem;
import com.restaurant.management.service.DishService;
import com.restaurant.management.service.OrderService;
import com.restaurant.management.service.PaymentService;
import com.restaurant.management.util.JSONConvertUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private DishService dishService;

    @GetMapping
    public String showMenu(Model model) {
        List<Dish> menuItems = dishService.getAllDishes();
        model.addAttribute("menuItems", menuItems);

        String menuItemsJsonString = JSONConvertUtil.entityToJSON(menuItems);

        System.out.println("Generated JSON: " + menuItemsJsonString);

        model.addAttribute("menuItemsJson", menuItemsJsonString);

        return "pages/menu";
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Map<String, Object> orderData) {
        String orderId = orderService.createOrder(orderData);
        return ResponseEntity.ok(orderId);
    }

    @GetMapping("/pay/{orderId}")
    public ResponseEntity<String> pay(@PathVariable("orderId") String orderId) {
        String redirectUrl = orderService.payOrder(orderId);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(redirectUrl)).build();
    }

    @GetMapping("/reviews/{orderId}")
    public String showReviews(@PathVariable String orderId ,Model model) {
        model.addAttribute("order", orderService.getOrderById(orderId));
        return "pages/reviews";
    }

    @PostMapping("/reviews/{orderId}")
    public String updateReview(@PathVariable("orderId") String orderId, @ModelAttribute Order order, Model model) {
        Order existingOrder = orderService.getOrderById(orderId);

        existingOrder.setRating(order.getRating());
        existingOrder.setComment(order.getComment());

        orderService.saveOrder(existingOrder);

        model.addAttribute("message", "Your review has been updated!");
        model.addAttribute("order", existingOrder);
        return "pages/reviews";
    }
}
