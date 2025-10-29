package com.entreprise.inventaire.repository;

import com.entreprise.inventaire.entity.Materiel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface MaterielRepository extends JpaRepository<Materiel, Long> {
    List<Materiel> findByType(String type);
    List<Materiel> findByEtat(String etat);
    Optional<Materiel> findByRef(String ref);
    
    @Query("SELECT m FROM Materiel m WHERE m.id NOT IN (SELECT a.materiel.id FROM Affectation a WHERE a.statut = 'Active')")
    List<Materiel> findMaterielNonAffecte();
    
    @Query("SELECT COUNT(m) FROM Materiel m WHERE m.etat = :etat")
    long countByEtat(@Param("etat") String etat);
}
