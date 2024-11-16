package com.example.gestionhotel.Controllers.Client;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookinController {
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/booking")
    String index( Model model) {
        return "booking";
    }
}
