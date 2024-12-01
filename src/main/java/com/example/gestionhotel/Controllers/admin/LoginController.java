package com.example.gestionhotel.Controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showlogin() {
        return "login"; // Retourne la vue login.html
    }

}
