package com.entreprise.inventaire.service;

import com.entreprise.inventaire.entity.*;
import com.entreprise.inventaire.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class InventaireService {
    
    @Autowired
    private MaterielRepository materielRepository;
    
    @Autowired
    private EmployeRepository employeRepository;
    
    @Autowired
    private AffectationRepository affectationRepository;
    
    // MATERIEL METHODS
    public List<Materiel> getAllMateriel() {
        return materielRepository.findAll();
    }
    
    public Materiel saveMateriel(Materiel materiel) {
        return materielRepository.save(materiel);
    }
    
    public Optional<Materiel> getMaterielById(Long id) {
        return materielRepository.findById(id);
    }
    
    public List<Materiel> getMaterielByType(String type) {
        return materielRepository.findByType(type);
    }
    
    public List<Materiel> getMaterielByEtat(String etat) {
        return materielRepository.findByEtat(etat);
    }
    
    public List<Materiel> getMaterielNonAffecte() {
        return materielRepository.findMaterielNonAffecte();
    }
    
    public void deleteMateriel(Long id) {
        materielRepository.deleteById(id);
    }
    
    // EMPLOYE METHODS
    public List<Employe> getAllEmployes() {
        return employeRepository.findAll();
    }
    
    public Employe saveEmploye(Employe employe) {
        return employeRepository.save(employe);
    }
    
    public Optional<Employe> getEmployeById(Long id) {
        return employeRepository.findById(id);
    }
    
    public List<Employe> getEmployesByService(String service) {
        return employeRepository.findByService(service);
    }
    
    public List<Employe> getEmployesSansAffectation() {
        return employeRepository.findEmployesSansAffectation();
    }
    
    public void deleteEmploye(Long id) {
        employeRepository.deleteById(id);
    }
    
    // AFFECTATION METHODS
    public List<Affectation> getAllAffectations() {
        return affectationRepository.findAll();
    }
    
    public Affectation saveAffectation(Affectation affectation) {
        return affectationRepository.save(affectation);
    }
    
    public List<Affectation> getAffectationsByService(String service) {
        return affectationRepository.findByEmployeService(service);
    }
    
    public List<Affectation> getAffectationsByStatut(String statut) {
        return affectationRepository.findByStatut(statut);
    }
    
    public boolean affecterMateriel(Long materielId, Long employeId) {
        Optional<Materiel> materielOpt = materielRepository.findById(materielId);
        Optional<Employe> employeOpt = employeRepository.findById(employeId);
        
        if (materielOpt.isPresent() && employeOpt.isPresent()) {
            Optional<Affectation> affectationExistante = affectationRepository.findActiveAffectationByMateriel(materielId);
            
            if (affectationExistante.isEmpty()) {
                Materiel materiel = materielOpt.get();
                Employe employe = employeOpt.get();
                
                Affectation affectation = new Affectation(materiel, employe, LocalDate.now());
                affectationRepository.save(affectation);
                return true;
            }
        }
        return false;
    }
    
    public boolean retournerMateriel(Long materielId) {
        Optional<Affectation> affectationOpt = affectationRepository.findActiveAffectationByMateriel(materielId);
        
        if (affectationOpt.isPresent()) {
            Affectation affectation = affectationOpt.get();
            affectation.setDateFin(LocalDate.now());
            affectation.setStatut("Retournée");
            affectationRepository.save(affectation);
            return true;
        }
        return false;
    }
    
    // STATISTICS METHODS
    public long getTotalMateriel() {
        return materielRepository.count();
    }
    
    public long getMaterielAffecte() {
        return affectationRepository.countActiveAffectations();
    }
    
    public double getTauxUtilisation() {
        long total = getTotalMateriel();
        if (total == 0) return 0;
        return (double) getMaterielAffecte() / total * 100;
    }
    
    public Map<String, Long> getPannesParCategorie() {
        List<Materiel> materielsEnPanne = materielRepository.findByEtat("En panne");
        return materielsEnPanne.stream()
            .collect(Collectors.groupingBy(Materiel::getType, Collectors.counting()));
    }
    
    public Map<String, Long> getMaterielParType() {
        return materielRepository.findAll().stream()
            .collect(Collectors.groupingBy(Materiel::getType, Collectors.counting()));
    }
    
    public Map<String, Long> getAffectationsParService() {
        return affectationRepository.findAll().stream()
            .filter(a -> "Active".equals(a.getStatut()))
            .collect(Collectors.groupingBy(
                a -> a.getEmploye().getService(), 
                Collectors.counting()
            ));
    }
}
