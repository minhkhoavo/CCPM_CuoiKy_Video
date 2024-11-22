package com.restaurant.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chefViewOder")
public class ChefViewOrderController {
    @Autowired
    private DishController dishController;

    @GetMapping
    public String listOrder(Model model) {
        //model.addAttribute("customers", customerService.getAllCustomer());
        // return "pages/customer/list";
        return "pages/admin/ChefViewOder/viewOrder";
    }

}
