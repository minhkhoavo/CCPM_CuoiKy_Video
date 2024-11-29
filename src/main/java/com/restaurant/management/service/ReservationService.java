package com.restaurant.management.service;

import com.restaurant.management.enums.ReservationStatus;
import com.restaurant.management.model.DiningTable;
import com.restaurant.management.model.Reservation;
import com.restaurant.management.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private TableService tableService;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
    public List<Reservation> findWithFilters(String search, String dateFilter, String status) {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .filter(reservation -> {
                    if (search != null && !search.isEmpty()) {
                        boolean matchesSearch = reservation.getCustomerName().toLowerCase().contains(search.toLowerCase()) ||
                                reservation.getEmail().toLowerCase().contains(search.toLowerCase()) ||
                                reservation.getNote().toLowerCase().contains(search.toLowerCase());
                        if (!matchesSearch) return false;
                    }

                    if (status != null && !status.isEmpty()) {
                        ReservationStatus enumStatus = ReservationStatus.valueOf(status);
                        if (!reservation.getStatus().equals(enumStatus)) {
                            return false;
                        }
                    }

                    if (dateFilter != null && !dateFilter.isEmpty()) {
                        LocalDate today = LocalDate.now();
                        LocalDate reservationDate = reservation.getDateToCome();

                        switch (dateFilter) {
                            case "today":
                                if (!reservationDate.isEqual(today)) return false;
                                break;
                            case "tomorrow":
                                if (!reservationDate.isEqual(today.plusDays(1))) return false;
                                break;
                            case "week":
                                LocalDate weekEnd = today.plusDays(7);
                                if (reservationDate.isBefore(today) || reservationDate.isAfter(weekEnd)) return false;
                                break;
                            case "month":
                                if (reservationDate.getMonth() != today.getMonth() ||
                                        reservationDate.getYear() != today.getYear()) return false;
                                break;
                        }
                    }

                    return true;
                })
                .collect(Collectors.toList());
    }

    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found!"));
    }

    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public void cancelReservation(Long tableId) {
        Reservation reservation = findById(tableId);
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);    }

    public void acceptReservation(Long tableId) {
        Reservation reservation = findById(tableId);
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservationRepository.save(reservation);
    }

    public boolean isTableAvailable(Long tableId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<DiningTable> availableTables = tableService.findAvailableTables(date, startTime);
        return availableTables.stream().anyMatch(table -> table.getId().equals(tableId));
    }

    public long countPendingReservations() {
        return reservationRepository.countByStatus(ReservationStatus.PENDING);
    }

    public long countTodayReservations() {
        return reservationRepository.countByDateToCome(LocalDate.now());
    }

    public long countConfirmedReservations() {
        return reservationRepository.countByStatus(ReservationStatus.CONFIRMED);
    }
}