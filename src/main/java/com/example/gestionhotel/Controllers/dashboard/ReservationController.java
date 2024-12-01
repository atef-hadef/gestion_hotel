package com.example.gestionhotel.Controllers.dashboard;

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
    private UserRepository userRepository;

    @Autowired
    private ChambreRepository chambreRepository;

    @Autowired
    private ReservationRepository reservationRepository;


    // Afficher la page de tableau de bord avec les réservations
    @GetMapping("/dashboardr")
    public String afficherDashboard(Model model) {
        // Récupérer toutes les réservations depuis le service
        List<Reservation> reservations = reservationService.getAllReservations();

        // Ajouter la liste des réservations au modèle pour l'afficher dans la vue
        model.addAttribute("reservations", reservations);

        // Retourner le nom de la vue à afficher (dashboard)
        return "reservation";
    }
    // Add reservation
    @GetMapping("/dashboard/ajouter")
    public String showAddReservationForm(Model model) {
        List<Chambre> chambres = chambreRepository.findAll();
        model.addAttribute("chambres", chambres);

        return "reservation"; //template for adding reservations
    }
    // Conversion de LocalDate à java.util.Date
    private Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    // Ajouter une réservation
    @PostMapping("/dashboard/ajouter")
    public String ajouterReservation(
                                 @RequestParam("numero_ch") Integer numero_ch,
                                 @RequestParam("date_arrive") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date_arrive,
                                 @RequestParam("date_sortir") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date_sortir,
                                 @RequestParam("nbr_personne") Integer nbr_personne,
                                 @RequestParam("montant_total") Double montant_total,
                                 @RequestParam("tel") Integer tel,
                                 @RequestParam("cin") Integer cin,
                                 @RequestParam("nom_client") String nom_client){

        // Convertir LocalDate en Date
        Date dateArriveConverted = convertToDate(date_arrive);
        Date dateSortirConverted = convertToDate(date_sortir);

        // Récupérer la chambre
        Chambre chambre = chambreRepository.findById(numero_ch).orElseThrow(() -> new IllegalArgumentException("Chambre introuvable"));

        // Créer la réservation
        Reservation reservation = new Reservation();
        reservation.setChambre(chambre);
        reservation.setDate_arrive(dateArriveConverted);
        reservation.setDate_sortir(dateSortirConverted);
        reservation.setNbr_personne(nbr_personne);
        reservation.setMontant_total(montant_total);// Calculer le montant
        reservation.setTel(tel);
        reservation.setNom_client(nom_client);
        reservation.setCin(cin);

        // Sauvegarder la réservation
        reservationRepository.save(reservation);

        return "redirect:/dashboardr";  // Rediriger vers la liste des réservations
    }
    //////////////////////delete///////////////////////////////////

    @PostMapping("/dashboard/supprimer/{id}")
        public String deleteReservation(@PathVariable Integer id) {
            reservationService.deleteReservation(id);  // Ensure this method exists in your service
            return "redirect:/dashboardr";  // Redirect to the dashboard or wherever you need
        }

        //////////////////////////////////////////edit/////////////////////

    @GetMapping("/dashboard/editer")
    public String showEditReservationForm(@PathVariable("idReservation") Integer idReservation, Model model) {
        // Récupérer la réservation existante par son ID
        Reservation reservation = reservationRepository.findById(idReservation)
                .orElseThrow(() -> new IllegalArgumentException("Réservation introuvable"));

        // Récupérer toutes les chambres disponibles pour permettre la sélection
        List<Chambre> chambres = chambreRepository.findAll();

        // Ajouter les données nécessaires au modèle pour le formulaire
        model.addAttribute("reservation", reservation);
        model.addAttribute("chambres", chambres);

        // Retourner le nom de la vue qui affiche le formulaire de modification
        return "ajouterReservation";  // Remplacez ce nom par le nom de votre template Thymeleaf
    }

        // Méthode POST pour traiter les modifications
        @PostMapping("/dashboard/editer")
        public String editReservation(@PathVariable Integer idReservation,
                                      @RequestParam("numero_ch") Integer numero_ch,
                                      @RequestParam("date_arrive") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date_arrive,
                                      @RequestParam("date_sortir") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date_sortir,
                                      @RequestParam("nbr_personne") Integer nbr_personne,
                                      @RequestParam("montant_total") Double montant_total,
                                      @RequestParam("tel") Integer tel,
                                      @RequestParam("cin") Integer cin,
                                      @RequestParam("nom_client") String nom_client) {

            // Récupérer la réservation existante
            Reservation reservation = reservationRepository.findById(idReservation)
                    .orElseThrow(() -> new IllegalArgumentException("Réservation introuvable"));

            // Récupérer la chambre sélectionnée
            Chambre chambre = chambreRepository.findById(numero_ch)
                    .orElseThrow(() -> new IllegalArgumentException("Chambre introuvable"));

            // Mettre à jour les informations de la réservation
            reservation.setChambre(chambre);
            reservation.setDate_arrive(convertToDate(date_arrive));
            reservation.setDate_sortir(convertToDate(date_sortir));
            reservation.setNbr_personne(nbr_personne);
            reservation.setMontant_total(montant_total);
            reservation.setTel(tel);
            reservation.setCin(cin);
            reservation.setNom_client(nom_client);

            // Sauvegarder la réservation mise à jour
            reservationRepository.save(reservation);

            // Rediriger vers le tableau de bord des réservations
            return "redirect:/dashboardr";
        }


    // Méthode pour afficher le formulaire de modification
    @GetMapping("/editer/{id}")
    public String editReservation(@PathVariable Integer id, Model model) {
        // Récupère la réservation à partir de la base de données
        Reservation reservation = reservationService.findById(id);
        model.addAttribute("reservation", reservation);
        return "ajouterReservation";  // Cette page contient ton formulaire de modification
    }

    // Méthode pour traiter le formulaire de modification
    @PostMapping("/editer/{id}")
    public String updateReservation(@ModelAttribute Reservation reservation) {
        // Mets à jour la réservation dans la base de données
        reservationService.update(reservation);
        return "redirect:/dashboardr";  // Rediriger vers la page des réservations après la modification
    }




    }
