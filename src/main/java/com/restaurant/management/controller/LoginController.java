package com.restaurant.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String listEmployees(Model model) {
//        model.addAttribute("employees", employeeService.getAllEmployees());
        return "pages/login/login";
    }
}
