package com.example.gestionhotel.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReservation;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date_arrive;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date_sortir;

    private Integer nbr_personne;

    private Double montant_total;
    private String nom_client;
    private Integer tel;
    private Integer cin;

    @ManyToOne
    @JoinColumn(name = "numero_ch", referencedColumnName = "numero_ch")
    private Chambre chambre;

}


