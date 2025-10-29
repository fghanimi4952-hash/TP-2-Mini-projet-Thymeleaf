package com.entreprise.inventaire.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "materiel")
public class Materiel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String ref;
    
    private String type;
    private String marque;
    private String etat;
    private LocalDate dateAchat;
    
    @OneToMany(mappedBy = "materiel", cascade = CascadeType.ALL)
    private List<Affectation> affectations = new ArrayList<>();
    
    public Materiel() {}
    
    public Materiel(String ref, String type, String marque, String etat, LocalDate dateAchat) {
        this.ref = ref;
        this.type = type;
        this.marque = marque;
        this.etat = etat;
        this.dateAchat = dateAchat;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getRef() { return ref; }
    public void setRef(String ref) { this.ref = ref; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getMarque() { return marque; }
    public void setMarque(String marque) { this.marque = marque; }
    
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
    
    public LocalDate getDateAchat() { return dateAchat; }
    public void setDateAchat(LocalDate dateAchat) { this.dateAchat = dateAchat; }
    
    public List<Affectation> getAffectations() { return affectations; }
    public void setAffectations(List<Affectation> affectations) { this.affectations = affectations; }
    
    @Override
    public String toString() {
        return ref + " - " + type + " (" + marque + ")";
    }
}
