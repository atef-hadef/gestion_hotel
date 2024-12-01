package com.example.gestionhotel.Controllers.dashboard;

import com.example.gestionhotel.Models.Chambre;
import com.example.gestionhotel.Models.Reservation;
import com.example.gestionhotel.Models.Users;
import com.example.gestionhotel.Repository.ChambreRepository;
import com.example.gestionhotel.Repository.ReservationRepository;
import com.example.gestionhotel.Repository.UserRepository;
import com.example.gestionhotel.Service.ChambreService;
import com.example.gestionhotel.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Controller
public class ChambreController {

    @Autowired
    private ChambreService chambreService;

    @Autowired
    private UserRepository userRepository;  // Injection de UserRepository

    @Autowired
    private ChambreRepository chambreRepository;  // Injection de ChambreRepository

    @Autowired
    private ReservationRepository reservationRepository;  // Injection de ReservationRepository


    // Afficher la page de tableau de bord avec les réservations
    @GetMapping("/chambre")
    public String afficherChambres(Model model) {
        // Récupérer toutes les réservations depuis le service
        List<Chambre> chambres = chambreService.getAllChambres();

        // Ajouter la liste des réservations au modèle pour l'afficher dans la vue
        model.addAttribute("chambres", chambres);

        // Retourner le nom de la vue à afficher (dashboard)
        return "chambre";
    }
    // Add chambres
    @GetMapping("/chambre/ajouter")
    public String showAddChambreForm(Model model) {
        model.addAttribute("chambres", chambreRepository.findAll());
        return "chambre"; //  template for adding chambres
    }
    // Ajouter une chambre
    @PostMapping("/chambre/ajouter")
    public String addChambre(
                                 @RequestParam("capacite") Integer capacite,
                                 @RequestParam("equipements") String equipements,
                                 @RequestParam("tarif") Double tarif,
                                 @RequestParam("type") String type,
                                 @RequestParam("image") String image){


        // Créer la chambre
        Chambre chambre = new Chambre();
        chambre.setCapacite(capacite);
        chambre.setType(type);
        chambre.setTarif(tarif);
        chambre.setImage(image);
        chambre.setEquipements(equipements);

        // Sauvegarder la réservation
        chambreRepository.save(chambre);

        return "redirect:/chambre";  // Rediriger vers la liste des réservations
    }
    @PostMapping("/chambre/supprimer/{id}")
    public String deleteReservation(@PathVariable Integer id) {
        chambreService.deleteChambre(id);  // Ensure this method exists in your service
        return "redirect:/chambre";  // Redirect to the dashboard or wherever you need
    }

    @GetMapping("/modifier/{id}")
    public String afficherFormulaireModification(@PathVariable Integer id, Model model) {
        chambreService.getChambreById(id).ifPresent(chambre -> model.addAttribute("chambre", chambre));
        return "formulaire";
    }

    @PostMapping("/modifier/{id}")
    public String modifierChambre(@PathVariable Integer id, @ModelAttribute Chambre chambre) {
        chambre.setNumero_ch(id);
        chambreService.ajouterChambres(chambre);
        return "redirect:/chambre";
    }

}
