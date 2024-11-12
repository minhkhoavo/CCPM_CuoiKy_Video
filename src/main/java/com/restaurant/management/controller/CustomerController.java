package com.restaurant.management.controller;

import com.restaurant.management.model.Customer;
import com.restaurant.management.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String listCustomer(Model model) {
        model.addAttribute("customers", customerService.getAllCustomer());
       // return "pages/customer/list";
       return "pages/admin/Customer/customerList";
    }

    @PostMapping("/add")
    public String addCustomer(@ModelAttribute Customer customer) {
        try {
            Customer savedCustomer = customerService.saveCustomer(customer);
        }
        catch (Exception e) {
            System.err.println("Error saving customer: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/customers";

    }

    @PostMapping("/edit/{id}")
    public String editCustomer(@PathVariable Long id, @ModelAttribute Customer customer) {
//        customer.setCustomerId(id);
//        Customer updatedCustomer = customerService.updateCustomer(customer);
//       // return ResponseEntity.ok(updatedCustomer);
        try {
            customer.setCustomerId(id);
            Customer updatedCustomer = customerService.updateCustomer(customer);

            return "redirect:/customers";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }
}
