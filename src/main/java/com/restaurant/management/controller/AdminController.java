package com.restaurant.management.controller;

import com.restaurant.management.service.DishService;
import com.restaurant.management.service.OrderItemService;
import com.restaurant.management.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/dashboard")
public class AdminController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private DishService dishService;

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    public String showDashboard(Model model) {
        Map<String, Integer> orderStats = orderService.getOrderStatistics();
        Map<String, Integer> dishStats = orderItemService.getDishStatistics();
        Map<String, Object> costAndRevenue = orderItemService.getCostAndRevenueData(LocalDate.parse("2020-01-01").atStartOfDay(), LocalDate.parse("2030-01-01").atTime(23, 59, 59));
//        Map<String, Object> costAndRevenue = orderItemService.getCostAndRevenueData("2020-01-01", "2030-01-01");


        model.addAttribute("orderStats", orderStats);
        model.addAttribute("dishStats", dishStats);
        model.addAttribute("costAndRevenue", costAndRevenue);
        System.out.println("----"+orderStats);
        return "pages/admin/dashboard";
    }
}
