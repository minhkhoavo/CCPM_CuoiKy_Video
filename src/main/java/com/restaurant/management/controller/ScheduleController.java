package com.restaurant.management.controller;

import com.restaurant.management.model.Schedule;
import com.restaurant.management.service.ScheduleService;
import com.restaurant.management.service.SchedulingSolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/schedules")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

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

    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule,
                                                   @RequestParam(required = false) Long shiftId,
                                                   @RequestParam Long employeeId) {
        System.out.println(shiftId);
        Schedule createdSchedule = scheduleService.createSchedule(schedule, shiftId, employeeId);
        return new ResponseEntity<>(createdSchedule, HttpStatus.CREATED);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Long scheduleId) {
        return scheduleService.getScheduleById(scheduleId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<Schedule> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody Map<String, Object> updates) {

        LocalDate workingDate = LocalDate.parse((String) updates.get("workingDate"));
        Long employeeId = Long.valueOf(updates.get("employeeId").toString());
        Schedule updatedSchedule = scheduleService.updateSchedule(scheduleId, workingDate, employeeId);

        return ResponseEntity.ok(updatedSchedule);
    }
    @GetMapping("/config")
    public String sendMatrix(Model model) {
        return "pages/schedule/auto-scheduling-conf";
    }

    @PostMapping("/config")
    public ResponseEntity<?> submitMatrix(@RequestBody Map<String, Object> requestBody) {
        String startDate = (String) requestBody.get("startDate");
        int maxShiftPerDay = Integer.parseInt(requestBody.get("maxShiftPerDay").toString());
        int maxDeviationShift = Integer.parseInt(requestBody.get("maxDeviationShift").toString());
        int isConsecutiveShifts = Integer.parseInt(requestBody.get("isConsecutiveShifts").toString());

        List<List<Integer>> staffMatrix = (List<List<Integer>>) requestBody.get("staffMatrix");
        List<List<Integer>> chefMatrix = (List<List<Integer>>) requestBody.get("chefMatrix");
        scheduleService.autoSchedulingShitf(startDate, staffMatrix, maxShiftPerDay, maxDeviationShift, isConsecutiveShifts);
        return ResponseEntity.ok("success");
    }



}
