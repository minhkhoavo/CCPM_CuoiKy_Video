package com.restaurant.management.controller;

import com.google.zxing.WriterException;
import com.restaurant.management.model.DiningTable;
import com.restaurant.management.model.Order;
import com.restaurant.management.service.OrderService;
import com.restaurant.management.service.TableService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tables")
public class TableController {

    @Autowired
    private TableService tableService;
    @Autowired
    private OrderService orderService;

    @GetMapping
    public String listTables(Model model) {
        List<DiningTable> diningTables = tableService.getAllTables();
        model.addAttribute("tables", diningTables);
        model.addAttribute("diningTable", new DiningTable());
        return "pages/tables/tables";
    }
    @GetMapping("/{tableId}")
    public String getOrderByTable(@PathVariable("tableId") Long tableId, Model model) {
        Optional<Order> orderOptional = orderService.findOrderByTableId(tableId);

        if (orderOptional.isPresent()) {
            model.addAttribute("order", orderOptional.get());
            model.addAttribute("orderItems", orderOptional.get().getOrderItems());
            return "pages/tables/table-orders";
        } else {
            return "redirect:/menu";
        }
    }
    @PostMapping("/add")
    public String addTable(@ModelAttribute DiningTable diningTable, HttpServletRequest request) {
        try {
            tableService.saveTable(diningTable, request);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return "error";
        }
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
