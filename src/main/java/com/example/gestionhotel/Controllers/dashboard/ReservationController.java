package com.example.gestionhotel.Controllers.dashboard;

import com.example.gestionhotel.Models.Chambre;
import com.example.gestionhotel.Models.Reservation;
import com.example.gestionhotel.Repository.ChambreRepository;
import com.example.gestionhotel.Repository.ReservationRepository;
import com.example.gestionhotel.Repository.AdminRepository;
import com.example.gestionhotel.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Controller
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private AdminRepository userRepository;

    @Autowired
    private ChambreRepository chambreRepository;

    @Autowired
    private ReservationRepository reservationRepository;


    @GetMapping("/dashboardr")
    public String afficherDashboard(Model model) {
        List<Reservation> reservations = reservationService.getAllReservations();

        model.addAttribute("reservations", reservations);

        return "VueAdmin/reservation";
    }

    @GetMapping("/dashboard/ajouter")
    public String showAddReservationForm(Model model) {
        List<Chambre> chambres = chambreRepository.findAll();
        model.addAttribute("chambres", chambres);

        return "VueAdmin/reservation";
    }

    private Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @PostMapping("/dashboard/ajouter")
    public String ajouterReservation(
            @RequestParam("numero_ch") Integer numero_ch,
            @RequestParam("date_arrive") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date_arrive,
            @RequestParam("date_sortir") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date_sortir,
            @RequestParam("nbr_personne") Integer nbr_personne,
            @RequestParam("montant_total") Double montant_total,
            @RequestParam("tel") Integer tel,
            @RequestParam("cin") Integer cin,
            @RequestParam("nom_client") String nom_client,
            RedirectAttributes redirectAttributes) {

        if (!chambreRepository.existsById(numero_ch)) {
            redirectAttributes.addFlashAttribute("error", "Le numéro de chambre " + numero_ch + " n'existe pas.");
            return "redirect:/dashboardr"; // Redirection vers le formulaire
        }

        Date dateArriveConverted = convertToDate(date_arrive);
        Date dateSortirConverted = convertToDate(date_sortir);

        Chambre chambre = chambreRepository.findById(numero_ch).orElseThrow(() -> new IllegalArgumentException("Chambre introuvable"));

        Reservation reservation = new Reservation();
        reservation.setChambre(chambre);
        reservation.setDate_arrive(date_arrive);
        reservation.setDate_sortir(date_sortir);
        reservation.setNbr_personne(nbr_personne);
        reservation.setMontant_total(montant_total);
        reservation.setTel(tel);
        reservation.setNom_client(nom_client);
        reservation.setCin(cin);

        reservationRepository.save(reservation);

        redirectAttributes.addFlashAttribute("success", "Réservation ajoutée avec succès !");

        return "redirect:/dashboardr";




}

    @GetMapping("/check")
    public String showCheckForm(Model model) {
        return "VueAdmin/checkResult";
    }

    @PostMapping("/check")
    public String checkDisponibilite(@RequestParam Integer numero_ch,
                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date_arrive,
                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date_sortir,
                                     Model model) {
        String result = reservationService.checkDisponibilite(numero_ch, date_arrive, date_sortir);
        model.addAttribute("message", result);
        return "VueAdmin/checkResult";  // Retourner une vue avec le résultat
    }
    //////////////////////delete///////////////////////////////////

    @PostMapping("/dashboard/supprimer/{id}")
        public String deleteReservation(@PathVariable Integer id) {
            reservationService.deleteReservation(id);
            return "redirect:/dashboardr";
        }

        //////////////////////////////////////////edit/////////////////////

    @GetMapping("/edit/{id}")
        public String afficherFormulaireModification(@PathVariable Integer id, Model model) {
            reservationService.getReservationById(id).ifPresent(reservation -> model.addAttribute("reservation", reservation));
            List<Chambre> chambre = chambreRepository.findAll();
            model.addAttribute("chambre", chambre);
            return "VueAdmin/formulaireReservation";
        }

    @PostMapping("/edit/{id}")
    public String modifierReservation(@PathVariable Integer id, @ModelAttribute Reservation reservation) {
        reservation.setIdReservation(id);
        reservationService.ajouterReservation(reservation);
        return "redirect:/dashboardr";
    }

    @GetMapping("reservation/list")
    public String listreservation(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Reservation> reservations = reservationService.searchReservation(keyword);
        model.addAttribute("reservations", reservations);
        model.addAttribute("keyword", keyword);
        return "VueAdmin/reservation";
    }




    }
