package com.restaurant.management.controller;

import com.restaurant.management.model.Customer;
import com.restaurant.management.model.Inventory;
import com.restaurant.management.model.Supplier;
import com.restaurant.management.service.CustomerService;
import com.restaurant.management.service.InventoryService;
import com.restaurant.management.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/inventories")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public String listInventory(Model model) {
        model.addAttribute("inventories", inventoryService.getAllInventory());
        model.addAttribute("suppliers", supplierService.getAllSuppliers());

        // return "pages/customer/list";
        return "pages/admin/Inventory/inventoryList";

//        List<Inventory> inventories = inventoryService.getAllInventory();
//        List<Supplier> suppliers = supplierService.getAllSuppliers();
//        Map<Long, String> supplierMap = suppliers.stream()
//                .collect(Collectors.toMap(Supplier::getSupplierId, Supplier::getName));
//
//        // Truyền cả inventories và supplierMap vào model
//        model.addAttribute("inventories", inventories);
//        model.addAttribute("suppliers", inventories);
//
//        model.addAttribute("supplierMap", supplierMap);
//        return "pages/admin/Inventory/inventoryList";

    }

    @GetMapping("/delete/{id}")
    public String deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return "redirect:/inventories";
    }

    @PostMapping("/add")
    public String addInventory(@ModelAttribute Inventory inventory) {
        try {
            Inventory sup =  inventoryService.saveInventory(inventory);
        }
        catch (Exception e) {
            System.err.println("Error saving inventory: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/inventories";
    }

    @PostMapping("/edit/{id}")
    public String editCustomer(@PathVariable Long id, @ModelAttribute Inventory inventory) {
        try {
            inventory.setInventoryId(id);
            Inventory update = inventoryService.updateInventory(inventory);

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "redirect:/inventories";

    }
}
