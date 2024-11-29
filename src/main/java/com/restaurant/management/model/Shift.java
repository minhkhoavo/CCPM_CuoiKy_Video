package com.restaurant.management.model;

import com.restaurant.management.enums.ShiftType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shiftId;
    private String shiftName;
    @Enumerated(EnumType.STRING)
    private ShiftType shiftType;

    private LocalTime startTime;
    private LocalTime endTime;

    // Open Shift
    private Integer available;
    @Column(nullable = false)
    private int maxRegistration;
    private LocalDate workingDate;

    // Fixed Shitf
    @ManyToOne(optional = true,  fetch = FetchType.LAZY)
    private Employee employee;

    private boolean isActive;
}
