package com.restaurant.management.repository;

import com.restaurant.management.enums.ReservationStatus;
import com.restaurant.management.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    long countByStatus(ReservationStatus status);
    long countByDateToCome(LocalDate date);

    @Query("SELECT r FROM Reservation r WHERE " +
            "(:search IS NULL OR LOWER(r.customerName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(r.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(r.note) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
            "(:status IS NULL OR r.status = :status) AND " +
            "(:date IS NULL OR r.dateToCome = :date)")
    List<Reservation> findByFilters(@Param("search") String search,
                                    @Param("status") ReservationStatus status,
                                    @Param("date") LocalDate date);

}

