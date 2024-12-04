package com.restaurant.management.controller;

import com.restaurant.management.repository.SalaryRepository;
import com.restaurant.management.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;


@Controller
@RequestMapping("/salaries")
public class SalaryController {
    @Autowired
    private SalaryService salaryService;
    @Autowired
    private SalaryRepository salaryRepository;

    @GetMapping
    public String listSalaries(@RequestParam(value = "date", required = false) String date, Model model) {
        if (date != null && !date.isEmpty()) {
            LocalDate start = LocalDate.parse(date + "-01");
            LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
            model.addAttribute("salaries", salaryService.getSalariesByPayDate(start, end));
        }

        return "pages/salary/salary-list";
    }

    @PostMapping("/calculate")
    public String calculateSalary(@RequestParam String monthYear) {
        LocalDate start = LocalDate.parse(monthYear + "-01");
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        salaryService.calculateSalaries(start, end);
        return "redirect:/salaries?date=" + monthYear;
    }

    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable Long id,
                                 @RequestParam("hourlyRate") Double hourlyRate,
                                 @RequestParam("bonus") Double bonus) {
        salaryService.updateSalary(id, hourlyRate, bonus);
        return "redirect:/salaries";
    }

    @GetMapping("/pay")
    public String paySalary() {
        salaryService.payAllSalaries();
        return "redirect:/salaries";
    }
}

