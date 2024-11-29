package com.restaurant.management.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

    public String handleException(Exception ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "pages/error";
    }
}
