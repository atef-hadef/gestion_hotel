package com.example.gestionhotel.Controllers.Client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {
    @GetMapping("/about")
    String about() {
        return "about";
    }
}
