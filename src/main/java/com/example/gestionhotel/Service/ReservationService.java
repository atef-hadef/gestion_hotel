package com.example.gestionhotel.Service;


import com.example.gestionhotel.Models.Chambre;
import com.example.gestionhotel.Models.Reservation;
import com.example.gestionhotel.Repository.ReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    // Ajouter une nouvelle réservation
    public Reservation ajouterReservation(Reservation reservation) {
        return reservationRepository.save(reservation);

    }
    // Récupérer toutes les réservations
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // Méthode pour supprimer une réservation
    public void deleteReservation(Integer id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);  // Supprimer la réservation par son ID
        } else {
            throw new RuntimeException("Réservation non trouvée avec ID " + id);
        }

    }

    public Optional<Reservation> getReservationById(Integer id) {
        return reservationRepository.findById(id);
    }
}
