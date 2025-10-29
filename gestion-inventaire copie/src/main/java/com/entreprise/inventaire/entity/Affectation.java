package com.entreprise.inventaire.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "affectation")
public class Affectation {
    @EmbeddedId
    private AffectationKey id = new AffectationKey();
    
    @ManyToOne
    @MapsId("materielId")
    @JoinColumn(name = "materiel_id")
    private Materiel materiel;
    
    @ManyToOne
    @MapsId("employeId")
    @JoinColumn(name = "employe_id")
    private Employe employe;
    
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String statut;
    
    public Affectation() {}
    
    public Affectation(Materiel materiel, Employe employe, LocalDate dateDebut) {
        this.materiel = materiel;
        this.employe = employe;
        this.dateDebut = dateDebut;
        this.statut = "Active";
        this.id = new AffectationKey(materiel.getId(), employe.getId());
    }
    
    public AffectationKey getId() { return id; }
    public void setId(AffectationKey id) { this.id = id; }
    
    public Materiel getMateriel() { return materiel; }
    public void setMateriel(Materiel materiel) { 
        this.materiel = materiel;
        if (id != null && materiel != null) {
            id.setMaterielId(materiel.getId());
        }
    }
    
    public Employe getEmploye() { return employe; }
    public void setEmploye(Employe employe) { 
        this.employe = employe;
        if (id != null && employe != null) {
            id.setEmployeId(employe.getId());
        }
    }
    
    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}
