package com.entreprise.inventaire.repository;

import com.entreprise.inventaire.entity.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface EmployeRepository extends JpaRepository<Employe, Long> {
    List<Employe> findByService(String service);
    Optional<Employe> findByEmail(String email);
    
    @Query("SELECT e FROM Employe e WHERE e.id NOT IN (SELECT a.employe.id FROM Affectation a WHERE a.statut = 'Active')")
    List<Employe> findEmployesSansAffectation();
}
