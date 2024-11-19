package com.restaurant.management.controller;

import com.restaurant.management.model.Reservation;
import com.restaurant.management.service.ReservationService;
import com.restaurant.management.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

//    @GetMapping
//    public String showReservations() {
//        List<Reservation> filteredReservations = reservationService.getAllReservations();
//        return "pages/reservation/viewreser";
//    }

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