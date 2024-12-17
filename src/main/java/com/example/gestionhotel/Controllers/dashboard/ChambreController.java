package com.example.gestionhotel.Controllers.dashboard;

import com.example.gestionhotel.Models.Chambre;
import com.example.gestionhotel.Repository.ChambreRepository;
import com.example.gestionhotel.Repository.ReservationRepository;
import com.example.gestionhotel.Repository.AdminRepository;
import com.example.gestionhotel.Service.ChambreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ChambreController {

    @Autowired
    private ChambreService chambreService;

    @Autowired
    private AdminRepository userRepository;

    @Autowired
    private ChambreRepository chambreRepository;

    @Autowired
    private ReservationRepository reservationRepository;


    @GetMapping("/chambre")
    public String afficherChambres(Model model) {
        List<Chambre> chambres = chambreService.getAllChambres();

        model.addAttribute("chambres", chambres);


        return "VueAdmin/chambre";
    }

    @GetMapping("/chambre/ajouter")
    public String showAddChambreForm(Model model) {
        model.addAttribute("chambres", chambreRepository.findAll());
        return "VueAdmin/chambre";
    }

    @PostMapping("/chambre/ajouter")
    public String addChambre(
                                 @RequestParam("capacite") Integer capacite,
                                 @RequestParam("equipements") String equipements,
                                 @RequestParam("tarif") Double tarif,
                                 @RequestParam("type") String type,
                                 @RequestParam("image") String image){



        Chambre chambre = new Chambre();
        chambre.setCapacite(capacite);
        chambre.setType(type);
        chambre.setTarif(tarif);
        chambre.setImage(image);
        chambre.setEquipements(equipements);

        chambreRepository.save(chambre);

        return "redirect:/chambre";
    }
    @PostMapping("/chambre/supprimer/{id}")
    public String deleteReservation(@PathVariable Integer id) {
        chambreService.deleteChambre(id);
        return "redirect:/chambre";
    }

    @GetMapping("/modifier/{id}")
    public String afficherFormulaireModification(@PathVariable Integer id, Model model) {
        chambreService.getChambreById(id).ifPresent(chambre -> model.addAttribute("chambre", chambre));
        return "VueAdmin/formulaire";
    }

    @PostMapping("/modifier/{id}")
    public String modifierChambre(@PathVariable Integer id, @ModelAttribute Chambre chambre) {
        chambre.setNumero_ch(id);
        chambreService.ajouterChambres(chambre);
        return "redirect:/chambre";
    }

    @GetMapping("chambre/list")
    public String listChambres(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Chambre> chambres = chambreService.searchChambres(keyword);
        model.addAttribute("chambres", chambres);
        model.addAttribute("keyword", keyword);
        return "VueAdmin/chambre";
    }

}
