package com.restaurant.management.controller;

import com.restaurant.management.model.Schedule;
import com.restaurant.management.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/schedules")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;
//
    @GetMapping
    public ResponseEntity<Map<String, List<Schedule>>> getSchedules(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate
    ) {
        Map<String, List<Schedule>> schedules = scheduleService.getSchedulesByDateRange(startDate, endDate);
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/view")
    public String viewSchedules(Model model) {
        return "pages/schedule/view-schedule";
    }
}
