package com.restaurant.management.controller;

import com.restaurant.management.model.Customer;
import com.restaurant.management.model.Employee;
import com.restaurant.management.model.Supplier;
import com.restaurant.management.service.CustomerService;
import com.restaurant.management.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/suppliers")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public String listSupplier(Model model) {
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        // return "pages/customer/list";
        return "pages/admin/Supplier/supplierList";
    }

    @PostMapping("/add")
    public String addSupplier(@ModelAttribute Supplier supplier) {
        try {
               Supplier sup =  supplierService.saveSupplier(supplier);
        }
        catch (Exception e) {
            System.err.println("Error saving supplier: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/suppliers";
    }

    @GetMapping("/delete/{id}")
    public String deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return "redirect:/suppliers";
    }

    @PostMapping("/edit/{id}")
    public String editSupplier(@PathVariable Long id, @ModelAttribute Supplier supplier) {
        try {
            supplier.setSupplierId(id);
            Supplier updatedsupplier = supplierService.updateSupplier(supplier);

            return "redirect:/suppliers";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
