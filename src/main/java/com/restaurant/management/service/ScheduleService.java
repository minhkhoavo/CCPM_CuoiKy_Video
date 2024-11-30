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
        if (shiftId != null) {
            Shift shift = shiftService.getShift(shiftId);
            schedule.setShift(shift);
        }

        Optional<Employee> employee = employeeService.getEmployeeById(employeeId);
        if (employee.isPresent()) {
            schedule.setEmployee(employee.get());
        }

        return scheduleRepository.save(schedule);  // Lưu Schedule vào DB
    }

    public void createSchedulesForFixedShift(Shift shift) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(1);

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            Schedule schedule = Schedule.builder()
                    .shift(shift)
                    .employee(shift.getEmployee())
                    .startTime(shift.getStartTime())
                    .endTime(shift.getEndTime())
                    .workingDate(date)
                    .status("DRAFT")
                    .build();

            scheduleRepository.save(schedule);
        }
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

    public Schedule registerEmployeeToShift(Long employeeId, Long shiftId) {
        Shift shift = shiftService.registerForShift(shiftId);
        Optional<Employee> employee = employeeService.getEmployeeById(employeeId);
        Schedule schedule = Schedule.builder()
                .employee(employee.get())
                .shift(shift)
                .startTime(shift.getStartTime())
                .endTime(shift.getEndTime())
                .status("DRAFT")
                .workingDate(shift.getWorkingDate())
                .build();

        return scheduleRepository.save(schedule);
    }

    public void cancelRegistration(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found."));
        shiftService.cancelShiftRegistration(schedule.getShift().getShiftId());
        scheduleRepository.delete(schedule);
    }

}
