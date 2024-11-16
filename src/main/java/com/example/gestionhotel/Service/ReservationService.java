package com.example.gestionhotel.Service;


import com.example.gestionhotel.Models.Reservation;
import com.example.gestionhotel.Repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
