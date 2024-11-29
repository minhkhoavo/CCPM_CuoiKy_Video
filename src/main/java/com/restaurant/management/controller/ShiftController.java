package com.restaurant.management.controller;

import com.restaurant.management.service.ScheduleService;
import com.restaurant.management.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shifts")
public class ShiftController {
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private ShiftService shiftService;

    @GetMapping
    private String showShiftList(Model model) {
        model.addAttribute("shiftList", shiftService.findAllShift());
        model.addAttribute("openShifts", shiftService.getOpenShifts());
        model.addAttribute("fixedShifts", shiftService.getFixedShifts());

        return "pages/schedule/shift-list";
    }
}
