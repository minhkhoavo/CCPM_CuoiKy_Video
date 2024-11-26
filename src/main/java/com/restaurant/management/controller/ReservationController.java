package com.restaurant.management.controller;

import com.restaurant.management.model.DiningTable;
import com.restaurant.management.model.Reservation;
import com.restaurant.management.service.ReservationService;
import com.restaurant.management.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private TableService tableService;

    @GetMapping("")
    public String listReservations(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String dateFilter,
            @RequestParam(required = false) String status,
            Model model
    ) {
        List<Reservation> filteredReservations = reservationService.findWithFilters(search, dateFilter, status);
        model.addAttribute("reservations", filteredReservations);
        return "pages/reservation/view-list";
    }
    @GetMapping("/form")
    public String reservationForm(Model model) {
        model.addAttribute("tables", tableService.getAllTables());
        model.addAttribute("reservation", new Reservation());
        return "pages/reservation/reservation-form";
    }

    @PostMapping("/check-availability")
    public String checkAvailability(
            @ModelAttribute("reservation") Reservation reservation,
            Model model) {
        LocalDate date = reservation.getDateToCome();
        LocalTime time = reservation.getTimeToCome();
        Long tableId = reservation.getTable().getId();

        LocalTime startTime = time;
        LocalTime endTime = time.plusHours(2);

        boolean isAvailable = reservationService.isTableAvailable(tableId, date, startTime, endTime);
        String message = isAvailable ? "Table is available for reservation!" : "The selected table is not available at this time.";
        model.addAttribute("availabilityMessage", message);
        model.addAttribute("available", isAvailable);
        model.addAttribute("tables", tableService.getAllTables());
        return "pages/reservation/reservation-form";
    }

    @GetMapping("/create")
    public String showReservationForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("tables", tableService.getAllTables());
        return "pages/reservation/form";
    }


    @PostMapping("/create")
    public String createReservation(@ModelAttribute Reservation reservation, RedirectAttributes redirectAttributes) {
        reservationService.save(reservation);
        return "redirect:/reservations";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Reservation reservation = reservationService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid reservation ID: " + id));
        model.addAttribute("tables", tableService.getAllTables());
        model.addAttribute("reservation", reservation);
        return "pages/reservation/form";
    }

    @PostMapping("/edit/{id}")
    public String updateReservation(@PathVariable Long id, @ModelAttribute Reservation reservation, RedirectAttributes redirectAttributes) {
        reservation.setId(id);
        reservationService.save(reservation);
        return "redirect:/reservations";
    }

    @GetMapping("/delete/{id}")
    public String deleteReservation(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        reservationService.deleteById(id);
        return "redirect:/reservations";
    }
}