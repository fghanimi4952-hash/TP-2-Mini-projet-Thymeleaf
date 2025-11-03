# Parc Informatique - Système de Gestion d'Inventaire

##  1. Description du projet

### Contexte fonctionnel du domaine choisi

Ce projet concerne la **gestion du parc informatique** d'une entreprise marocaine. Le système permet de suivre l'inventaire complet du matériel informatique (PC, imprimantes, écrans, serveurs, etc.) et de gérer son affectation aux employés de l'organisation.

### Objectif de l'application

L'application a pour objectif de :
- Centraliser la gestion du matériel informatique d'une entreprise
- Suivre les affectations de matériel aux employés
- Fournir des statistiques en temps réel sur l'utilisation du parc informatique
- Faciliter la traçabilité et la maintenance du matériel

### Public cible / Cas d'usage

**Public cible :**
- Service informatique / IT Manager
- Responsables du parc informatique
- Administrateurs systèmes
- Direction générale (pour le tableau de bord et les statistiques)

**Cas d'usage principaux :**
- Affecter du matériel à un nouvel employé
- Suivre l'état des équipements (OK, En panne, En réparation)
- Identifier le matériel disponible pour affectation
- Consulter les statistiques d'utilisation du parc
- Retourner du matériel en fin d'affectation
- Gérer les employés et leurs services

### Ce que l'application permet concrètement

**L'application permet de gérer l'inventaire informatique complet d'une entreprise, depuis l'enregistrement du matériel jusqu'à son affectation aux employés, avec suivi en temps réel des statistiques et de l'état des équipements.**

---

##  2. Architecture technique

### 2.1 Stack technologique

- **Backend :** Spring Boot 3.2.3, Spring Data JPA/Hibernate
- **Frontend :** Thymeleaf, HTML5/CSS3, Bootstrap 5.1.3, JavaScript (ES6)
- **Base de données :** MySQL 8.0
- **Build :** Maven 4.0.0
- **Java :** Version 21
- **Autres technologies :**
  - Font Awesome 6.0.0 (icônes)
  - Spring DevTools (rechargement automatique)

### 2.2 Structure du code

```
src/main/java/com/entreprise/inventaire/
├── entity/              # Classes JPA (modèle de données)
│   ├── Employe.java
│   ├── Materiel.java
│   ├── Affectation.java
│   └── AffectationKey.java  (clé composite)
├── repository/          # Interfaces d'accès aux données (Spring Data JPA)
│   ├── EmployeRepository.java
│   ├── MaterielRepository.java
│   └── AffectationRepository.java
├── service/            # Logique métier
│   └── InventaireService.java
├── controller/         # Contrôleurs web MVC
│   └── InventaireController.java
└── InventaireApplication.java  (classe principale)

src/main/resources/
├── templates/          # Vues Thymeleaf
│   ├── accueil.html
│   ├── employes/
│   │   ├── liste.html
│   │   └── form.html
│   ├── materiel/
│   │   ├── liste.html
│   │   └── form.html
│   └── affectations/
│       └── liste.html
├── static/             # Ressources statiques
│   └── css/
│       └── style.css   (styles personnalisés)
└── application.properties  (configuration)
```

### 2.3 Diagramme d'architecture

<img width="1024" height="1536" alt="im1" src="https://github.com/user-attachments/assets/eefeb484-43ff-42cc-9667-5ca550a34470" />

**Flux de traitement :**
1. Le navigateur envoie une requête HTTP au contrôleur Spring MVC
2. Le contrôleur appelle le service pour la logique métier
3. Le service utilise les repositories pour accéder aux données
4. Les repositories exécutent des requêtes JPA/Hibernate vers MySQL
5. Les données remontent au service puis au contrôleur
6. Le contrôleur injecte les données dans un template Thymeleaf
7. Thymeleaf génère le HTML final renvoyé au navigateur

---

##  3. Fonctionnalités principales

### 3.1 CRUD sur les entités principales

#### Gestion des Employés
-  **Création :** Ajouter un nouvel employé (nom, service, email)
-  **Lecture :** Consulter la liste de tous les employés avec leurs informations
-  **Suppression :** Supprimer un employé (avec vérification des affectations actives)

#### Gestion du Matériel
-  **Création :** Ajouter du matériel (référence, type, marque, état, date d'achat)
-  **Lecture :** Consulter la liste de tous les équipements avec leurs caractéristiques
-  **Suppression :** Supprimer du matériel (avec vérification des affectations actives)

#### Gestion des Affectations
-  **Création :** Affecter du matériel disponible à un employé
-  **Lecture :** Consulter toutes les affectations (actives et retournées)
-  **Mise à jour :** Retourner du matériel (changement de statut et date de fin)

### 3.2 Recherche / Filtrage

#### Filtrage du Matériel
- **Par type :** PC Portable, PC Bureau, Imprimante, Écran, Souris, Clavier, Tablette, Serveur
- **Par état :** OK, En panne, En réparation
- Affichage du matériel disponible (non affecté)

#### Filtrage des Affectations
- **Par service :** Direction, Informatique, Comptabilité, Commercial, Marketing, etc.
- **Par statut :** Active, Retournée
- Affichage des employés sans affectation
- Affichage du matériel non affecté

### 3.3 Tableau de bord / Statistiques

Le tableau de bord affiche des **statistiques dynamiques** qui se mettent à jour automatiquement toutes les 5 secondes :

**Indicateurs principaux :**
-  **Matériel total** : Nombre total d'équipements en stock
-  **Matériel affecté** : Nombre d'équipements actuellement utilisés
-  **Taux d'utilisation** : Pourcentage de matériel utilisé
-  **Matériel en panne** : Nombre d'équipements défectueux
-  **Total employés** : Nombre total d'employés
-  **Employés avec matériel** : Nombre d'employés équipés
-  **Matériel disponible** : Nombre d'équipements non affectés
-  **En réparation** : Nombre d'équipements en maintenance

**Graphiques dynamiques :**
- Répartition du matériel par type (graphique en barres avec pourcentages)
- Répartition des affectations par service (graphique en barres avec pourcentages)

**API REST pour statistiques :**
- Endpoint `/api/statistiques` retournant toutes les statistiques en JSON pour mise à jour en temps réel

### 3.4 Gestion des statuts

**Statuts du Matériel :**
- `OK` : Matériel en bon état et opérationnel
- `En panne` : Matériel défectueux nécessitant une réparation
- `En réparation` : Matériel actuellement en maintenance

**Statuts des Affectations :**
- `Active` : Affectation en cours, matériel actuellement utilisé
- `Retournée` : Affectation terminée, matériel retourné et disponible

**Règles métier :**
- Un matériel ne peut être affecté qu'une seule fois (vérification des affectations actives)
- Un employé avec affectation active ne peut être supprimé
- Un matériel affecté ne peut être supprimé

---

##  4. Modèle de données

### 4.1 Entités

#### 1. Employe
| Attribut | Type | Description | Contraintes |
|----------|------|-------------|-------------|
| `id` | Long | Identifiant unique | AUTO_INCREMENT, PRIMARY KEY |
| `nom` | String(100) | Nom complet de l'employé | NOT NULL |
| `service` | String(50) | Service de l'employé | NOT NULL |
| `email` | String(100) | Email professionnel | NOT NULL, UNIQUE |

**Exemple :** Fatima Zahra Alaoui, Direction, fatima.alaoui@entreprise.ma

#### 2. Materiel
| Attribut | Type | Description | Contraintes |
|----------|------|-------------|-------------|
| `id` | Long | Identifiant unique | AUTO_INCREMENT, PRIMARY KEY |
| `ref` | String(50) | Référence unique | NOT NULL, UNIQUE |
| `type` | String(50) | Type d'équipement | NOT NULL |
| `marque` | String(50) | Marque du matériel | NOT NULL |
| `etat` | String(20) | État (OK/En panne/En réparation) | NOT NULL |
| `dateAchat` | LocalDate | Date d'achat | NOT NULL |

**Exemple :** PC-MAR-001, PC Portable, Dell, OK, 2023-01-15

#### 3. Affectation
| Attribut | Type | Description | Contraintes |
|----------|------|-------------|-------------|
| `id` | AffectationKey | Clé composite (materielId, employeId) | PRIMARY KEY |
| `materiel` | Materiel | Référence au matériel | NOT NULL, @ManyToOne |
| `employe` | Employe | Référence à l'employé | NOT NULL, @ManyToOne |
| `dateDebut` | LocalDate | Date de début d'affectation | NOT NULL |
| `dateFin` | LocalDate | Date de fin d'affectation | NULLABLE |
| `statut` | String(20) | Statut (Active/Retournée) | NOT NULL |

**Clé composite :** `AffectationKey(materielId, employeId)` - Un employé ne peut avoir qu'une seule affectation active pour un même matériel

### 4.2 Relations

**Schéma des relations :**

```
Employe (1) ────< (N) Affectation (N) >─── (1) Materiel
```

- **Employe → Affectation :** Relation `@OneToMany`
  - Un employé peut avoir plusieurs affectations (historique)
  - Mappée par `mappedBy = "employe"`

- **Materiel → Affectation :** Relation `@OneToMany`
  - Un matériel peut avoir plusieurs affectations (historique)
  - Mappée par `mappedBy = "materiel"`

- **Affectation → Employe :** Relation `@ManyToOne`
  - Une affectation concerne un seul employé
  - Clé étrangère : `employe_id`

- **Affectation → Materiel :** Relation `@ManyToOne`
  - Une affectation concerne un seul matériel
  - Clé étrangère : `materiel_id`

**Contraintes d'intégrité :**
- Clé composite sur `(materiel_id, employe_id)` pour garantir l'unicité
- Cascade `ALL` sur les relations pour maintenir la cohérence
- Contrôle applicatif pour une seule affectation active par matériel

### 4.3 Configuration base de données

**URL de connexion :**
```
jdbc:mysql://localhost:3306/gestion_inventaire?useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true
```

**Identifiants par défaut :**
- **Username :** `root`
- **Password :** (vide par défaut)
- **Base de données :** `gestion_inventaire` (créée automatiquement si elle n'existe pas)

**Stratégie de génération des tables :**
```properties
spring.jpa.hibernate.ddl-auto=create-drop
```
 **Note :** Pour la production, utiliser `update` pour préserver les données :
```properties
spring.jpa.hibernate.ddl-auto=update
```

**Tables créées automatiquement :**
- `employe` : Liste des employés
- `materiel` : Inventaire du matériel
- `affectation` : Historique des affectations

---

##  5. Lancer le projet

### 5.1 Prérequis

**Logiciels nécessaires :**
- **Java :** Version 21 ou supérieure
  ```bash
  java -version  # Vérifier la version
  ```
- **Maven :** Version 3.6+ 
  ```bash
  mvn -version  # Vérifier la version
  ```
- **MySQL :** Version 8.0 ou supérieure
  ```bash
  mysql --version  # Vérifier la version
  ```

**Configuration MySQL :**
- MySQL Server doit être démarré et accessible sur `localhost:3306`
- Un utilisateur avec les droits appropriés (par défaut : `root`)

### 5.2 Installation

#### Étape 1 : Cloner ou télécharger le projet
```bash
git clone <url-du-repo>
cd gestion-inventaire-mysql
```

#### Étape 2 : Configurer `application.properties`
Modifier si nécessaire le fichier `src/main/resources/application.properties` :

```properties
# Adapter selon votre configuration MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/gestion_inventaire?useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=votre_mot_de_passe  # Si nécessaire
```

**Important :** Après le premier démarrage réussi, changer `ddl-auto` :
```properties
spring.jpa.hibernate.ddl-auto=update  # Au lieu de create-drop
```

#### Étape 3 : Lancer l'application

**Option A : Via Maven**
```bash
mvn clean install
mvn spring-boot:run
```

**Option B : Via l'IDE**
- Ouvrir le projet dans IntelliJ IDEA ou Eclipse
- Exécuter la classe `InventaireApplication.java`

**Option C : Via le JAR**
```bash
mvn clean package
java -jar target/inventaire-mysql-1.0.0.jar
```

### 5.3 Accès

**Page d'accueil / Tableau de bord :**
```
http://localhost:8080
```

**URLs principales :**
- **Gestion du matériel :** `http://localhost:8080/materiel`
- **Gestion des employés :** `http://localhost:8080/employes`
- **Gestion des affectations :** `http://localhost:8080/affectations`
- **Initialisation des données :** `http://localhost:8080/init`
- **API Statistiques (JSON) :** `http://localhost:8080/api/statistiques`

** Important :** Accéder à `/init` pour charger les données de test (10 employés marocains + 25 équipements avec affectations).

---

##  6. Jeu de données initial

### Chargement automatique des données

L'application inclut un système de chargement automatique de données de test via le contrôleur :

**Méthode de chargement :**
- Endpoint : `GET /init`
- Méthode : `InventaireService.chargerDonneesTest()`
- Condition : Ne charge que si la base de données est vide (aucun matériel ni employé)

### Données insérées automatiquement

#### 10 Employés marocains

| Nom | Service | Email |
|-----|---------|-------|
| Fatima Zahra Alaoui | Direction | fatima.alaoui@entreprise.ma |
| Mehdi Benjelloun | Informatique | mehdi.benjelloun@entreprise.ma |
| Amina El Fassi | Comptabilité | amina.elfassi@entreprise.ma |
| Youssef Chraibi | Commercial | youssef.chraibi@entreprise.ma |
| Khadija Bouziane | Ressources Humaines | khadija.bouziane@entreprise.ma |
| Omar Touimi | Marketing | omar.touimi@entreprise.ma |
| Noura Belhaj | Support Technique | noura.belhaj@entreprise.ma |
| Karim Idrissi | Informatique | karim.idrissi@entreprise.ma |
| Salma Bennis | Marketing | salma.bennis@entreprise.ma |
| Hassan Amrani | Commercial | hassan.amrani@entreprise.ma |

#### 25 Équipements informatiques

**PC Portables (4) :**
- PC-MAR-001 (Dell), PC-MAR-003 (Apple), PC-MAR-004 (HP), PC-MAR-005 (Lenovo)

**PC Bureaux (3) :**
- PC-MAR-002 (Lenovo), PC-BUR-001 (Dell), PC-BUR-002 (HP)

**Imprimantes (3) :**
- IMP-CLR-001 (HP), IMP-CLR-002 (Canon), IMP-NB-001 (Brother - En panne)

**Écrans (4) :**
- ECR-27-001 (Samsung - En panne), ECR-32-001 (LG), ECR-24-001 (Dell), ECR-27-002 (ASUS)

**Souris (4) :**
- SOUR-GM-001 (Logitech), SOUR-GM-002 (Microsoft), SOUR-GM-003 (Razer), SOUR-GM-004 (Logitech - En réparation)

**Claviers (3) :**
- CLAV-MEC-001 (Razer), CLAV-MEC-002 (Logitech), CLAV-MEC-003 (Corsair)

**Tablettes (2) :**
- TAB-AND-001 (Samsung), TAB-IPD-001 (Apple)

**Serveurs (2) :**
- SRV-LOC-001 (Dell), SRV-LOC-002 (HP)

#### Affectations initiales

- **Direction :** PC Portable Dell + Écran LG
- **Informatique :** MacBook + Souris + Clavier + Écran (Mehdi) | PC HP + Souris + Clavier (Karim)
- **Comptabilité :** PC Bureau + Imprimante HP
- **Commercial :** PC Portable + Tablette (Youssef) | PC Bureau + Tablette (Hassan)
- **Marketing :** Écran + Souris (Omar) | PC Bureau + Imprimante (Salma)
- **Support Technique :** 2 Serveurs

---

##  7. Démonstration (Vidéo)

### Lien vers la vidéo de démonstration
> **https://drive.google.com/file/d/14Ikd4GmWNEiQHegpsj4imMeZjndSBAJw/view?usp=sharing**

### Contenu de la vidéo

La vidéo de démonstration montre :

1. **Navigation dans les pages**
   - Accès au tableau de bord
   - Navigation entre les différentes sections (Matériel, Employés, Affectations)

2. **Création d'un enregistrement**
   - Ajout d'un nouvel employé avec exemples marocains
   - Ajout de matériel avec référence marocaine (ex: PC-MAR-006)
   - Affectation de matériel à un employé

3. **Recherche / Filtrage**
   - Filtrage du matériel par type (PC Portable, Imprimante, etc.)
   - Filtrage par état (OK, En panne)
   - Filtrage des affectations par service

4. **Dashboard / Statistiques**
   - Affichage des statistiques dynamiques (mise à jour automatique)
   - Graphiques du matériel par type
   - Graphiques des affectations par service
   - Visualisation des indicateurs en temps réel

5. **Fonctionnalités avancées**
   - Retour de matériel (changement de statut)
   - Suppression avec vérification des contraintes
   - Messages de confirmation et d'erreur

---

##  8. Auteurs / Encadrement

### Auteur
> **GHANIMI Fatima ezzahra**  
> **f.ghanimi4952@uca.ac.ma**

### Encadrement
> **Encadrant : Dr. Mohamed LACHGAR** 
>
>  **Cours : Développement JakartaEE : Spring** 
> **Établissement : ENS MARRAKECH** 

### Année académique
> **Année :** 2025-2026

---

## Notes supplémentaires

### Fonctionnalités techniques avancées

- **Statistiques dynamiques :** Mise à jour automatique toutes les 5 secondes via AJAX
- **Design moderne :** Interface responsive avec animations CSS
- **Validation métier :** Contrôles applicatifs pour éviter les affectations multiples
- **Gestion des erreurs :** Messages clairs pour l'utilisateur
- **API REST :** Endpoint JSON pour les statistiques

### Technologies utilisées

- **Frontend :** HTML5, CSS3 (animations, gradients), Bootstrap 5, JavaScript ES6, Font Awesome
- **Backend :** Spring Boot 3.2.3, Spring MVC, Spring Data JPA
- **Base de données :** MySQL 8.0
- **Build :** Maven

### Licence
Ce projet est à usage éducatif et académique.

---


