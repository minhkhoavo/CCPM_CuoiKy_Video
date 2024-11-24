package com.restaurant.management.service;

import com.restaurant.management.model.Shift;
import com.restaurant.management.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShiftService {
    @Autowired
    private ShiftRepository shiftRepository;

    public List<Shift> findAllShift() {
        return shiftRepository.findAll();
    }

    public Shift createShift(Shift shift) {
        return shiftRepository.save(shift);
    }

    public Shift getShift(Long id) {
        return shiftRepository.findById(id).get();
    }
}
