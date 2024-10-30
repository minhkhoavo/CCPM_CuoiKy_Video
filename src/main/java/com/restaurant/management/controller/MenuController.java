package com.restaurant.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.management.model.Dish;
import com.restaurant.management.service.DishService;
import com.restaurant.management.util.JSONConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private DishService dishService;

    @Autowired
    private ObjectMapper objectMapper;  // Jackson ObjectMapper

    @GetMapping
    public String showMenu(Model model) {
        List<Dish> menuItems = dishService.getAllDishes();
        model.addAttribute("menuItems", menuItems);

        String menuItemsJsonString = JSONConvertUtil.entityToJSON(menuItems);

        System.out.println("Generated JSON: " + menuItemsJsonString);

        model.addAttribute("menuItemsJson", menuItemsJsonString);

        return "pages/menu";
    }

}
