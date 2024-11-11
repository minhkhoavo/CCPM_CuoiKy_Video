package com.restaurant.management.controller;

import com.restaurant.management.model.Dish;
import com.restaurant.management.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/dishes")
public class DishController {

    @Autowired
    private DishService dishService;

    // Hiển thị danh sách các món ăn
    @GetMapping
    public String listDishes(Model model) {
        model.addAttribute("dishes", dishService.getAllDishes());
        return "pages/admin/dish";
    }

    // Hiển thị form thêm món ăn (optional, nếu bạn muốn form thêm riêng)
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("dish", new Dish());
        return "pages/dish/add";
    }

    // Thêm món ăn mới
    @PostMapping("/add")
    public String addDish(@ModelAttribute Dish dish,
                          @RequestParam("file") MultipartFile file) {
        try {
            System.out.println(dish);
            dishService.saveDish(dish, file);
            return "redirect:/dishes";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/dishes";
        }
    }

    // Hiển thị form sửa món ăn
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Dish dish = dishService.getDishById(id)
                .orElseThrow(() -> new RuntimeException("Dish not found with id: " + id));
        model.addAttribute("dish", dish);
        return "pages/dish/edit";
    }

    // Cập nhật món ăn
    @PostMapping("/edit/{id}")
    public String updateDish(@PathVariable Long id,
                             @ModelAttribute Dish dish,
                             @RequestParam("file") MultipartFile file) {
        try {
            dishService.updateDish(id, dish, file);
            return "redirect:/dishes";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteDish(@PathVariable Long id) {
        dishService.deleteDish(id);
        return "redirect:/dishes";
    }

    // Lọc món ăn theo danh mục (nếu cần)
    @GetMapping("/category/{categoryId}")
    public String getDishesByCategory(@PathVariable Long categoryId, Model model) {
        model.addAttribute("dishes", dishService.getDishesByCategory(categoryId));
        return "pages/dish/list";
    }
}

