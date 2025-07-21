package com.example.gestionhotel.Service;


import com.example.gestionhotel.Models.Chambre;
import com.example.gestionhotel.Models.Reservation;
import com.example.gestionhotel.Repository.ChambreRepository;
import com.example.gestionhotel.Repository.ReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ChambreRepository chambreRepository;


    public Reservation ajouterReservation(Reservation reservation) {
        return reservationRepository.save(reservation);

    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public void deleteReservation(Integer id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Réservation non trouvée avec ID " + id);
        }

    }

    public Optional<Reservation> getReservationById(Integer id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> searchReservation(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            return reservationRepository.findByKeyword(keyword);
        }
        return reservationRepository.findAll();
    }

    public String checkDisponibilite(Integer numero_ch, LocalDate date_arrive, LocalDate date_sortir) {
        // Récupérer la chambre
        Optional<Chambre> chambreOpt = chambreRepository.findById(numero_ch);
        if (!chambreOpt.isPresent()) {
            return "Chambre introuvable.";
        }

        // Vérifier si la chambre est déjà réservée dans l'intervalle donné
        List<Reservation> reservations = reservationRepository.findByChambreAndDateRange(numero_ch, date_arrive, date_sortir);

        if (!reservations.isEmpty()) {
            // Si des réservations existent, trouver la dernière date de réservation
            LocalDate dernierJour = reservations.stream()
                    .map(Reservation::getDate_sortir)
                    .max(LocalDate::compareTo)
                    .orElseThrow(() -> new RuntimeException("Erreur de date de réservation"));
            return "La chambre "+numero_ch+" n'est pas disponible. La dernière réservation se termine le : " + dernierJour;
        }

        return "La chambre est disponible.";
    }
}
