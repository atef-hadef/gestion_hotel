package com.example.gestionhotel.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idReservation;

    private Date date_arrive;
    private Date date_sortir;
    private Integer nbr_personne;

    private Double montant_total;

    @ManyToOne
    @JoinColumn(name = "numero_ch", referencedColumnName = "numero_ch")
    private Chambre chambre;

    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Users user;
}


