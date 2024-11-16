package com.example.gestionhotel.Controllers.admin;

import com.example.gestionhotel.Models.Chambre;
import com.example.gestionhotel.Models.Reservation;
import com.example.gestionhotel.Models.Users;
import com.example.gestionhotel.Repository.ChambreRepository;
import com.example.gestionhotel.Repository.ReservationRepository;
import com.example.gestionhotel.Repository.UserRepository;
import com.example.gestionhotel.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserRepository userRepository;  // Injection de UserRepository

    @Autowired
    private ChambreRepository chambreRepository;  // Injection de ChambreRepository

    @Autowired
    private ReservationRepository reservationRepository;  // Injection de ReservationRepository


    // Afficher la page de tableau de bord avec les réservations
    @GetMapping("/dashboard")
    public String afficherDashboard(Model model) {
        // Récupérer toutes les réservations depuis le service
        List<Reservation> reservations = reservationService.getAllReservations();

        // Ajouter la liste des réservations au modèle pour l'afficher dans la vue
        model.addAttribute("reservations", reservations);

        // Retourner le nom de la vue à afficher (dashboard)
        return "dashboard";
    }
    // Add reservation
    @GetMapping("/dashboard/ajouter")
    public String showAddReservationForm(Model model) {
        model.addAttribute("chambres", chambreRepository.findAll());
        return "ajouterReservation"; // This should be the name of your Thymeleaf template for adding reservations
    }
    // Conversion de LocalDate à java.util.Date
    private Date convertToDate(LocalDate localDate) {
        return java.util.Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    // Ajouter une réservation
    @PostMapping("/dashboard/ajouter")
    public String addReservation(@RequestParam("id") Integer id,
                                 @RequestParam("numero_ch") Integer numero_ch,
                                 @RequestParam("date_arrive") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date_arrive,
                                 @RequestParam("date_sortir") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date_sortir,
                                 @RequestParam("nbr_personne") Integer nbr_personne,
                                 @RequestParam("montant_total") Double montant_total){

        // Convertir LocalDate en Date
        Date dateArriveConverted = convertToDate(date_arrive);
        Date dateSortirConverted = convertToDate(date_sortir);

        // Récupérer l'utilisateur et la chambre
        Users user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));
        Chambre chambre = chambreRepository.findById(numero_ch).orElseThrow(() -> new IllegalArgumentException("Chambre introuvable"));

        // Créer la réservation
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setChambre(chambre);
        reservation.setDate_arrive(dateArriveConverted);
        reservation.setDate_sortir(dateSortirConverted);
        reservation.setNbr_personne(nbr_personne);
        reservation.setMontant_total(montant_total);  // Calculer le montant

        // Sauvegarder la réservation
        reservationRepository.save(reservation);

        return "redirect:/dashboard";  // Rediriger vers la liste des réservations
    }

}
