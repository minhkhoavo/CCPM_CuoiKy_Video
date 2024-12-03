package com.restaurant.management.controller;

import com.restaurant.management.model.Dish;
import com.restaurant.management.model.Order;
import com.restaurant.management.model.OrderItem;
import com.restaurant.management.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TableService tableService;

    @GetMapping("/menu")
    public String showMenu(Model model,
                           @RequestParam("orderId") String orderId,
                           @RequestParam(value = "category", required = false) Long categoryId) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("activeCategory", categoryId);
        System.out.println("categoryId:::: " + categoryId);
        List<Dish> menuItems = (categoryId != null) ?
                dishService.findDishesByCategoryId(categoryId) :
                dishService.getAllDishes();
        model.addAttribute("menuItems", menuItems);
        model.addAttribute("orderId", orderId);
        List<OrderItem> cart = orderService.getOrderDetailByOrderId(orderId);
        model.addAttribute("cart", cart);
        double total = cart.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        model.addAttribute("total", total);
        return "pages/menu";
    }

    @GetMapping("/menu/search")
    public String searchDishes(@RequestParam("query") String query,
                               @RequestParam("orderId") String orderId,
                               Model model) {
        model.addAttribute("menuItems", dishService.searchDishes(query));
        model.addAttribute("query", query);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("orderId", orderId);
        model.addAttribute("activeCategory", 1L);
        List<OrderItem> cart = orderService.getOrderDetailByOrderId(orderId);
        model.addAttribute("cart", cart);
        double total = cart.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        model.addAttribute("total", total);
        return "pages/menu";
    }

    @PostMapping("/add")
    public String addToOrder(
            @RequestParam("orderId") String orderId,
            @RequestParam("dishId") Long dishId,
            @RequestParam("quantity") Integer quantity) {
        orderService.addDishToOrder(orderId, dishId, quantity);
        return "redirect:/orders/menu?orderId=" + orderId;
    }

    @PostMapping("/update")
    public String updateOrderItem(
            @RequestParam("orderId") String orderId,
            @RequestParam("orderItemId") Long orderItemId,
            @RequestParam("action") String action) {
        if ("increase".equals(action)) {
            orderService.updateOrderItemQuantity(orderItemId, 1);
        } else if ("decrease".equals(action)) {
            orderService.updateOrderItemQuantity(orderItemId, -1);
        }
        return "redirect:/orders/menu?orderId=" + orderId;
    }

    @GetMapping("/checkout")
    public String checkout(
            @RequestParam("orderId") String orderId,
            Model model) {
        List<OrderItem> cart = orderService.getOrderDetailByOrderId(orderId);
        model.addAttribute("orderItems", cart);
        double total = cart.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        model.addAttribute("total", total);
        model.addAttribute("orderId", orderId);
        return "pages/payment";
    }

    @GetMapping("/manage")
    public String showManageTableOrder(Model model,
                          @RequestParam(value = "tableId", required = false) Long tableId) {
        if(tableId != null) {
            Optional<Order> order = orderService.findOrderByTableId(tableId);
            if(order.isPresent()) {
                List<OrderItem> cart = orderService.getOrderDetailByOrderId(order.get().getId());
                model.addAttribute("cart", cart);
                double total = cart.stream()
                        .mapToDouble(item -> item.getPrice() * item.getQuantity())
                        .sum();
                model.addAttribute("total", total);
                model.addAttribute("orderId", order.get().getId());
            }
        }
        model.addAttribute("tables", tableService.getAllTables());
        return "pages/order/manage-table-order";
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
