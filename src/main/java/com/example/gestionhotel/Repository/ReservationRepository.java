package com.example.gestionhotel.Repository;

import com.example.gestionhotel.Models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

}
