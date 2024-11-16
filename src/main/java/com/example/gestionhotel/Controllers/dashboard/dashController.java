package com.example.gestionhotel.Controllers.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class dashController {
    @GetMapping("/dash")
    String index( Model model) {
        return "index";
    }
}
