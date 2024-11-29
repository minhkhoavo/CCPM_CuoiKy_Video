package com.restaurant.management.repository;

import com.restaurant.management.enums.ShiftType;
import com.restaurant.management.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShiftRepository extends JpaRepository<Shift, Long> {
    List<Shift> findByShiftType(ShiftType shiftType);
}
