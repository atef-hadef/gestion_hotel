package com.example.gestionhotel.Controllers.Client;

import com.example.gestionhotel.Models.Chambre;
import com.example.gestionhotel.Repository.ChambreRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class HomeControllers {
    private final ChambreRepository chambreRepository;

    public HomeControllers(ChambreRepository chambreRepository) {
        this.chambreRepository = chambreRepository;
    }

    @GetMapping("/")
    public String getAllRooms(Model model) {
        List<Chambre> chambres = chambreRepository.findAll();
        model.addAttribute("chambres", chambres);
        return "VueClient/home";
    }
}
