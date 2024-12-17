package com.example.gestionhotel.Repository;

import com.example.gestionhotel.Models.Chambre;
import com.example.gestionhotel.Models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findAll();

    @Query(value = "SELECT * FROM reservation r WHERE r.nom_client LIKE %:keyword%" ,
            nativeQuery = true)
    List<Reservation> findByKeyword(@Param("keyword") String keyword);

    // Cette méthode vérifie si une réservation existe dans la période donnée pour la chambre spécifiée
    @Query("SELECT r FROM Reservation r WHERE r.chambre.numero_ch = :numero_ch " +
            "AND ((r.date_arrive BETWEEN :date_arrive AND :date_sortir) " +
            "OR (r.date_arrive BETWEEN :date_arrive AND :date_sortir))")
    List<Reservation> findByChambreAndDateRange(@Param("numero_ch") Integer numero_ch,
                                                @Param("date_arrive") LocalDate date_arrive,
                                                @Param("date_sortir") LocalDate date_sortir);
}
