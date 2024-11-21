package com.restaurant.management.service;

import com.restaurant.management.model.Schedule;
import com.restaurant.management.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    public Map<String,List<Schedule>> getSchedulesByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Schedule> schedules = scheduleRepository.findByWorkingDateBetween(startDate, endDate);

        return schedules.stream().collect(
                Collectors.groupingBy(
                    schedule -> schedule.getWorkingDate().toString()
                )
        );
    }
}
