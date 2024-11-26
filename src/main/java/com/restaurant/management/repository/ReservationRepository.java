package com.restaurant.management.repository;

import com.restaurant.management.enums.ReservationStatus;
import com.restaurant.management.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    long countByStatus(ReservationStatus status);
    long countByDateToCome(LocalDate date);
}

