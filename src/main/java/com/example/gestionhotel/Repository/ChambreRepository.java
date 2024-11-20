package com.example.gestionhotel.Repository;

import com.example.gestionhotel.Models.Chambre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChambreRepository extends JpaRepository<Chambre, Integer> {
    List<Chambre> findAll();
}
