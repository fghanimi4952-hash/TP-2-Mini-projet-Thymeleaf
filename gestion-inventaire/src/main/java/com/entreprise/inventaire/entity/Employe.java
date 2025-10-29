package com.entreprise.inventaire.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employe")
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    private String service;
    
    @Column(unique = true)
    private String email;
    
    @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL)
    private List<Affectation> affectations = new ArrayList<>();
    
    public Employe() {}
    
    public Employe(String nom, String service, String email) {
        this.nom = nom;
        this.service = service;
        this.email = email;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getService() { return service; }
    public void setService(String service) { this.service = service; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public List<Affectation> getAffectations() { return affectations; }
    public void setAffectations(List<Affectation> affectations) { this.affectations = affectations; }
    
    @Override
    public String toString() {
        return nom + " - " + service;
    }
}
