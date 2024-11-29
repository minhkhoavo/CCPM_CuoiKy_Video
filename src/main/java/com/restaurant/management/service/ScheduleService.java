package com.restaurant.management.service;

import com.restaurant.management.model.Employee;
import com.restaurant.management.model.Schedule;
import com.restaurant.management.model.Shift;
import com.restaurant.management.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ShiftService shiftService;

    public Map<String,List<Schedule>> getSchedulesByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Schedule> schedules = scheduleRepository.findByWorkingDateBetween(startDate, endDate);

        return schedules.stream().collect(
                Collectors.groupingBy(
                    schedule -> schedule.getWorkingDate().toString()
                )
        );
    }

    public Schedule createSchedule(Schedule schedule, Long shiftId, Long employeeId) {
        if(shiftId != null) {
            Shift shift = shiftService.getShift(shiftId);
            schedule.setShift(shift);
        }
        Optional<Employee> employee = employeeService.getEmployeeById(employeeId);
        schedule.setEmployee(employee.get());
        schedule.setShift(null);
        return scheduleRepository.save(schedule);
    }

    public void deleteSchedule(Long scheduleId) {
        if (scheduleRepository.existsById(scheduleId)) {
            scheduleRepository.deleteById(scheduleId);
        } else {
            throw new IllegalArgumentException("Schedule not found with id: " + scheduleId);
        }
    }

    public Optional<Schedule> getScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId);
    }

    public Schedule updateSchedule(Long scheduleId, LocalDate workingDate, Long employeeId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found with id: " + scheduleId));

        schedule.setWorkingDate(workingDate);

        if (employeeId != null) {
            Employee employee = employeeService.getEmployeeById(employeeId)
                    .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + employeeId));
            schedule.setEmployee(employee);
        }

        return scheduleRepository.save(schedule);
    }

}
