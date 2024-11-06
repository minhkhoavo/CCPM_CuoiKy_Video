package com.restaurant.management.controller;

import com.restaurant.management.model.Order;
import com.restaurant.management.model.OrderItem;
import com.restaurant.management.service.DishService;
import com.restaurant.management.service.OrderService;
import com.restaurant.management.service.PaymentService;
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

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

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
}
