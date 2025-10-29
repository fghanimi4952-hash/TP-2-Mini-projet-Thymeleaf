package com.entreprise.inventaire.repository;

import com.entreprise.inventaire.entity.Affectation;
import com.entreprise.inventaire.entity.AffectationKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface AffectationRepository extends JpaRepository<Affectation, AffectationKey> {
    List<Affectation> findByEmployeService(String service);
    List<Affectation> findByStatut(String statut);
    
    @Query("SELECT a FROM Affectation a WHERE a.employe.service = :service AND a.statut = :statut")
    List<Affectation> findByServiceAndStatut(@Param("service") String service, @Param("statut") String statut);
    
    @Query("SELECT a FROM Affectation a WHERE a.materiel.id = :materielId AND a.statut = 'Active'")
    Optional<Affectation> findActiveAffectationByMateriel(@Param("materielId") Long materielId);
    
    @Query("SELECT a FROM Affectation a WHERE a.employe.id = :employeId AND a.statut = 'Active'")
    List<Affectation> findActiveAffectationsByEmploye(@Param("employeId") Long employeId);
    
    @Query("SELECT COUNT(a) FROM Affectation a WHERE a.statut = 'Active'")
    long countActiveAffectations();
}
