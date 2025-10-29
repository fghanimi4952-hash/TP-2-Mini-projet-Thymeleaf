package com.entreprise.inventaire.controller;

import com.entreprise.inventaire.entity.*;
import com.entreprise.inventaire.service.InventaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;
import java.util.Optional;

@Controller
public class InventaireController {
    
    @Autowired
    private InventaireService inventaireService;
    
    // === PAGES PRINCIPALES ===
    @GetMapping("/")
    public String accueil(Model model) {
        model.addAttribute("totalMateriel", inventaireService.getTotalMateriel());
        model.addAttribute("materielAffecte", inventaireService.getMaterielAffecte());
        model.addAttribute("tauxUtilisation", inventaireService.getTauxUtilisation());
        model.addAttribute("pannesParCategorie", inventaireService.getPannesParCategorie());
        model.addAttribute("materielParType", inventaireService.getMaterielParType());
        model.addAttribute("affectationsParService", inventaireService.getAffectationsParService());
        return "accueil";
    }
    
    // === GESTION MATERIEL ===
    @GetMapping("/materiel")
    public String listeMateriel(Model model) {
        model.addAttribute("materiels", inventaireService.getAllMateriel());
        model.addAttribute("materielNonAffecte", inventaireService.getMaterielNonAffecte());
        return "materiel/liste";
    }
    
    @GetMapping("/materiel/ajouter")
    public String formAjouterMateriel(Model model) {
        model.addAttribute("materiel", new Materiel());
        return "materiel/form";
    }
    
    @PostMapping("/materiel/sauvegarder")
    public String sauvegarderMateriel(@ModelAttribute Materiel materiel, 
                                    RedirectAttributes redirectAttributes) {
        try {
            inventaireService.saveMateriel(materiel);
            redirectAttributes.addFlashAttribute("success", "Matériel ajouté avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'ajout du matériel");
        }
        return "redirect:/materiel";
    }
    
    @GetMapping("/materiel/supprimer/{id}")
    public String supprimerMateriel(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            inventaireService.deleteMateriel(id);
            redirectAttributes.addFlashAttribute("success", "Matériel supprimé avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression");
        }
        return "redirect:/materiel";
    }
    
    @GetMapping("/materiel/filtrer")
    public String filtrerMateriel(@RequestParam(required = false) String type, 
                                 @RequestParam(required = false) String etat, 
                                 Model model) {
        if (type != null && !type.isEmpty()) {
            model.addAttribute("materiels", inventaireService.getMaterielByType(type));
        } else if (etat != null && !etat.isEmpty()) {
            model.addAttribute("materiels", inventaireService.getMaterielByEtat(etat));
        } else {
            model.addAttribute("materiels", inventaireService.getAllMateriel());
        }
        return "materiel/liste";
    }
    
    // === GESTION EMPLOYES ===
    @GetMapping("/employes")
    public String listeEmployes(Model model) {
        model.addAttribute("employes", inventaireService.getAllEmployes());
        model.addAttribute("employesSansAffectation", inventaireService.getEmployesSansAffectation());
        return "employes/liste";
    }
    
    @GetMapping("/employes/ajouter")
    public String formAjouterEmploye(Model model) {
        model.addAttribute("employe", new Employe());
        return "employes/form";
    }
    
    @PostMapping("/employes/sauvegarder")
    public String sauvegarderEmploye(@ModelAttribute Employe employe, 
                                   RedirectAttributes redirectAttributes) {
        try {
            inventaireService.saveEmploye(employe);
            redirectAttributes.addFlashAttribute("success", "Employé ajouté avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'ajout de l'employé");
        }
        return "redirect:/employes";
    }
    
    @GetMapping("/employes/supprimer/{id}")
    public String supprimerEmploye(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            inventaireService.deleteEmploye(id);
            redirectAttributes.addFlashAttribute("success", "Employé supprimé avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression");
        }
        return "redirect:/employes";
    }
    
    // === GESTION AFFECTATIONS ===
    @GetMapping("/affectations")
    public String listeAffectations(Model model) {
        model.addAttribute("affectations", inventaireService.getAllAffectations());
        model.addAttribute("materiels", inventaireService.getMaterielNonAffecte());
        model.addAttribute("employes", inventaireService.getEmployesSansAffectation());
        model.addAttribute("affectationsActives", inventaireService.getAffectationsByStatut("Active"));
        return "affectations/liste";
    }
    
    @PostMapping("/affectations/affecter")
    public String affecterMateriel(@RequestParam Long materielId, 
                                  @RequestParam Long employeId,
                                  RedirectAttributes redirectAttributes) {
        boolean success = inventaireService.affecterMateriel(materielId, employeId);
        if (success) {
            redirectAttributes.addFlashAttribute("success", "Matériel affecté avec succès !");
        } else {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'affectation - matériel peut-être déjà affecté");
        }
        return "redirect:/affectations";
    }
    
    @GetMapping("/affectations/retourner/{materielId}")
    public String retournerMateriel(@PathVariable Long materielId, 
                                   RedirectAttributes redirectAttributes) {
        boolean success = inventaireService.retournerMateriel(materielId);
        if (success) {
            redirectAttributes.addFlashAttribute("success", "Matériel retourné avec succès !");
        } else {
            redirectAttributes.addFlashAttribute("error", "Erreur lors du retour - matériel non affecté");
        }
        return "redirect:/affectations";
    }
    
    @GetMapping("/affectations/filtrer")
    public String filtrerAffectations(@RequestParam(required = false) String service,
                                     @RequestParam(required = false) String statut,
                                     Model model) {
        if (service != null && !service.isEmpty()) {
            model.addAttribute("affectations", inventaireService.getAffectationsByService(service));
        } else if (statut != null && !statut.isEmpty()) {
            model.addAttribute("affectations", inventaireService.getAffectationsByStatut(statut));
        } else {
            model.addAttribute("affectations", inventaireService.getAllAffectations());
        }
        return "affectations/liste";
    }
}
