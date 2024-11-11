package com.restaurant.management.controller;

import com.restaurant.management.model.DiningTable;
import com.restaurant.management.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tables")
public class TableController {

    @Autowired
    private TableService tableService;

    @GetMapping
    public String listTables(Model model) {
        List<DiningTable> diningTables = tableService.getAllTables();
        model.addAttribute("tables", diningTables);
        return "pages/admin/tables";
    }

    @PostMapping("/add")
    public String addTable(@ModelAttribute DiningTable diningTable) {
        tableService.saveTable(diningTable);
        return "redirect:/tables";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        DiningTable diningTable = tableService.getTableById(id).orElseThrow(() -> new IllegalArgumentException("Invalid table Id:" + id));
        model.addAttribute("table", diningTable);
        return "tables/edit"; // Thymeleaf template for editing a table
    }

    @PostMapping("/edit/{id}")
    public String editTable(@PathVariable Long id, @ModelAttribute DiningTable diningTable) {
        tableService.updateTable(id, diningTable);
        return "redirect:/tables";
    }


    @GetMapping("/delete/{id}")
    public String deleteTable(@PathVariable Long id) {
        tableService.deleteTable(id);
        return "redirect:/tables";
    }
}
