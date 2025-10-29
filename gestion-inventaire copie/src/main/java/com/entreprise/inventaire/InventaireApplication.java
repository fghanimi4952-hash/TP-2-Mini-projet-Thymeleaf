package com.entreprise.inventaire;

import com.entreprise.inventaire.entity.*;
import com.entreprise.inventaire.service.InventaireService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;

@SpringBootApplication
public class InventaireApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventaireApplication.class, args);
    }

    @Bean
    public CommandLineRunner chargerDonnees(InventaireService service) {
        return args -> {
            System.out.println("=== CHARGEMENT DES DONNÉES DE TEST MAROCAINES ===");
            

            Employe emp1 = new Employe("Fatima Zahra Alaoui", "Direction", "fatima.alaoui@entreprise.ma");
            Employe emp2 = new Employe("Mehdi Benjelloun", "Informatique", "mehdi.benjelloun@entreprise.ma");
            Employe emp3 = new Employe("Amina El Fassi", "Comptabilité", "amina.elfassi@entreprise.ma");
            Employe emp4 = new Employe("Youssef Chraibi", "Commercial", "youssef.chraibi@entreprise.ma");
            Employe emp5 = new Employe("Khadija Bouziane", "Ressources Humaines", "khadija.bouziane@entreprise.ma");
            Employe emp6 = new Employe("Omar Touimi", "Marketing", "omar.touimi@entreprise.ma");
            Employe emp7 = new Employe("Noura Belhaj", "Support Technique", "noura.belhaj@entreprise.ma");
            
            service.saveEmploye(emp1);
            service.saveEmploye(emp2);
            service.saveEmploye(emp3);
            service.saveEmploye(emp4);
            service.saveEmploye(emp5);
            service.saveEmploye(emp6);
            service.saveEmploye(emp7);
            
            //MATÉRIEL INFORMATIQUE
            Materiel mat1 = new Materiel("PC-MAR-001", "PC Portable", "Dell", "OK", LocalDate.of(2023, 1, 15));
            Materiel mat2 = new Materiel("IMP-CLR-001", "Imprimante", "HP", "OK", LocalDate.of(2023, 2, 20));
            Materiel mat3 = new Materiel("ECR-27-001", "Écran", "Samsung", "En panne", LocalDate.of(2023, 3, 10));
            Materiel mat4 = new Materiel("PC-MAR-002", "PC Bureau", "Lenovo", "En réparation", LocalDate.of(2023, 4, 5));
            Materiel mat5 = new Materiel("SOUR-GM-001", "Souris", "Logitech", "OK", LocalDate.of(2023, 5, 12));
            Materiel mat6 = new Materiel("CLAV-MEC-001", "Clavier", "Razer", "OK", LocalDate.of(2023, 6, 8));
            Materiel mat7 = new Materiel("PC-MAR-003", "PC Portable", "Apple", "OK", LocalDate.of(2023, 7, 20));
            Materiel mat8 = new Materiel("ECR-32-001", "Écran", "LG", "OK", LocalDate.of(2023, 8, 15));
            Materiel mat9 = new Materiel("TAB-AND-001", "Tablette", "Samsung", "OK", LocalDate.of(2023, 9, 10));
            Materiel mat10 = new Materiel("SRV-LOC-001", "Serveur", "Dell", "OK", LocalDate.of(2023, 10, 25));
            
            service.saveMateriel(mat1);
            service.saveMateriel(mat2);
            service.saveMateriel(mat3);
            service.saveMateriel(mat4);
            service.saveMateriel(mat5);
            service.saveMateriel(mat6);
            service.saveMateriel(mat7);
            service.saveMateriel(mat8);
            service.saveMateriel(mat9);
            service.saveMateriel(mat10);
            
            // AFFECTATIONS RÉALISTES
            service.affecterMateriel(mat1.getId(), emp1.getId()); // Fatima Zahra - PC Portable
            service.affecterMateriel(mat7.getId(), emp2.getId()); // Mehdi - MacBook
            service.affecterMateriel(mat5.getId(), emp2.getId()); // Mehdi - Souris
            service.affecterMateriel(mat6.getId(), emp2.getId()); // Mehdi - Clavier
            service.affecterMateriel(mat2.getId(), emp3.getId()); // Amina - Imprimante
            service.affecterMateriel(mat8.getId(), emp4.getId()); // Youssef - Écran 32"
            service.affecterMateriel(mat9.getId(), emp6.getId()); // Omar - Tablette
            
            System.out.println("Données de test marocaines chargées avec succès !");
            System.out.println(service.getTotalMateriel() + " matériels créés");
            System.out.println(service.getAllEmployes().size() + " employés marocains créés");
            System.out.println(service.getAllAffectations().size() + " affectations créées");
            System.out.println(" Application disponible sur: http://localhost:8080");
            System.out.println("Contexte marocain prêt !");
        };
    }
}
