package com.example.gestionhotel.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
public class Chambre {
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        private Integer numero_ch;

        private String type;
        private Integer capacite;
        private String equipements;
        private Double tarif;
        private String image;


}

