package com.example.gestionhotel.Repository;

import com.example.gestionhotel.Models.Chambre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChambreRepository extends JpaRepository<Chambre, Integer> {
    List<Chambre> findAll();
    @Query(value = "SELECT * FROM chambre c WHERE c.numero_ch LIKE %:keyword%" ,
            nativeQuery = true)
    List<Chambre> findByTypeContainingIgnoreCase(@Param("keyword") String keyword);

}
