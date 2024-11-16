package com.example.gestionhotel.Service;

import com.example.gestionhotel.Models.Chambre;
import com.example.gestionhotel.Models.Reservation;
import com.example.gestionhotel.Repository.ChambreRepository;
import com.example.gestionhotel.Repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ChambreService {
    @Autowired
    private ChambreRepository chambreRepository;

    // Ajouter une nouvelle réservation
    public Chambre ajouterChambres(Chambre chambre) {
        return chambreRepository.save(chambre);

    }

    // Récupérer toutes les réservations
    public List<Chambre> getAllChambres() {
        return chambreRepository.findAll();
    }
}
