# Architecture du Projet - Clean Architecture

## 🏗️ Vue d'ensemble

Ce document détaille l'architecture Clean Architecture utilisée dans le projet de gestion de bibliothèque.

## 📐 Principes de la Clean Architecture

### 1. Indépendance des frameworks
- Le métier ne dépend pas de Spring Boot
- On peut changer de framework sans toucher au domaine

### 2. Testabilité
- Chaque couche peut être testée indépendamment
- Pas besoin de base de données pour tester le métier

### 3. Indépendance de l'UI
- L'API REST peut être remplacée par GraphQL sans impact
- Le métier reste le même

### 4. Indépendance de la base de données
- On peut passer de H2 à PostgreSQL facilement
- Les entités domain ne connaissent pas JPA

## 🔄 Les 4 Couches

```
┌─────────────────────────────────────────────────┐
│           Frameworks & Drivers                   │
│  (Spring Boot, JPA, H2, Swagger)                │
└─────────────────┬───────────────────────────────┘
                  │
┌─────────────────┴───────────────────────────────┐
│         Interface Adapters                       │
│  (Controllers, Repository Adapters, Mappers)    │
└─────────────────┬───────────────────────────────┘
                  │
┌─────────────────┴───────────────────────────────┐
│         Application Business Rules               │
│  (Use Cases, Services, DTOs)                    │
└─────────────────┬───────────────────────────────┘
                  │
┌─────────────────┴───────────────────────────────┐
│      Enterprise Business Rules                   │
│  (Entities: Livre, Membre, Emprunt...)          │
└──────────────────────────────────────────────────┘
```

## 📦 Détail des Couches

### 1️⃣ Domain (Enterprise Business Rules)

**Rôle** : Le cœur de l'application, contient la logique métier pure

**Contenu** :
- `domain/entities/` : Entités métier (Livre, Membre, Emprunt, Reservation)
- `domain/repository/` : Interfaces des repositories (contrats)

**Règles** :
- ✅ Pas de dépendances externes (pas de Spring, pas de JPA)
- ✅ Logique métier pure (calculs, validations)
- ✅ Méthodes métier dans les entités

**Exemple - Livre.java** :
```java
public class Livre {
    private Long id;
    private String titre;
    private Integer nombreDisponibles;
    
    // Méthode métier
    public boolean estDisponible() {
        return nombreDisponibles > 0;
    }
    
    public void emprunter() {
        if (!estDisponible()) {
            throw new IllegalStateException("Livre non disponible");
        }
        nombreDisponibles--;
    }
}
```

### 2️⃣ Application (Application Business Rules)

**Rôle** : Orchestrer les cas d'utilisation de l'application

**Contenu** :
- `application/service/` : Services métier (use cases)
- `application/dto/` : Objets de transfert de données
- `application/mapper/` : Conversions Entity ↔ DTO

**Règles** :
- ✅ Utilise les entités du domain
- ✅ Dépend des interfaces de repository (pas des implémentations)
- ✅ Contient la logique applicative

**Exemple - EmpruntService.java** :
```java
@Service
public class EmpruntService {
    private final EmpruntRepository empruntRepository;
    private final LivreService livreService;
    
    public Emprunt emprunterLivre(Long livreId, Long membreId) {
        // 1. Vérifier disponibilité
        if (!livreService.estDisponible(livreId)) {
            throw new IllegalStateException("Livre non disponible");
        }
        
        // 2. Créer l'emprunt
        Emprunt emprunt = new Emprunt(...);
        
        // 3. Décrémenter le stock
        livreService.emprunterExemplaire(livreId);
        
        // 4. Sauvegarder
        return empruntRepository.save(emprunt);
    }
}
```

### 3️⃣ Adapters (Interface Adapters)

**Rôle** : Adapter les données entre les couches

**Contenu** :
- `adapters/controller/` : Controllers REST (API)
- `adapters/repository/` : Implémentation des repositories
- `adapters/infrastructure/` : Entités JPA et repositories JPA

**Règles** :
- ✅ Convertit les requêtes HTTP en appels de service
- ✅ Implémente les interfaces du domain
- ✅ Gère la persistance (JPA)

**Exemple - LivreController.java** :
```java
@RestController
@RequestMapping("/api/livres")
public class LivreController {
    private final LivreService livreService;
    
    @GetMapping
    public ResponseEntity<List<LivreDto>> obtenirTousLesLivres() {
        List<Livre> livres = livreService.obtenirTousLesLivres();
        return ResponseEntity.ok(LivreMapper.toDtoList(livres));
    }
}
```

**Exemple - LivreRepositoryAdapter.java** :
```java
@Component
public class LivreRepositoryAdapter implements LivreRepository {
    private final JpaLivreRepository jpaRepository;
    
    @Override
    public Livre save(Livre livre) {
        LivreEntity entity = toEntity(livre);
        LivreEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }
}
```

### 4️⃣ Frameworks & Drivers

**Rôle** : Outils et frameworks externes

**Contenu** :
- Spring Boot
- Spring Data JPA
- H2 Database
- Swagger/OpenAPI

**Règles** :
- ✅ Configuration dans application.properties
- ✅ Annotations Spring uniquement dans adapters
- ✅ Pas de code métier ici

## 🔄 Flux de données

### Exemple : Emprunter un livre

```
1. Client HTTP
   │
   │ POST /api/emprunts?livreId=1&membreId=2
   ▼
2. EmpruntController (Adapter)
   │
   │ Appelle le service
   ▼
3. EmpruntService (Application)
   │
   │ Logique métier
   ├─ Vérifie disponibilité
   ├─ Vérifie quota
   ├─ Crée l'emprunt
   ▼
4. Emprunt (Domain Entity)
   │
   │ Calcule date de retour
   ▼
5. EmpruntRepository (Domain Interface)
   │
   │ Interface
   ▼
6. EmpruntRepositoryAdapter (Adapter)
   │
   │ Implémentation JPA
   ▼
7. JpaEmpruntRepository (Spring Data JPA)
   │
   │ Sauvegarde en BDD
   ▼
8. H2 Database
```

## 🎯 Avantages de cette architecture

### ✅ Testabilité
```java
// Test du service sans base de données
@Test
void emprunterLivre_livreDisponible_success() {
    // Mock du repository
    EmpruntRepository mockRepo = mock(EmpruntRepository.class);
    EmpruntService service = new EmpruntService(mockRepo, ...);
    
    // Test
    Emprunt emprunt = service.emprunterLivre(1L, 2L);
    
    // Vérification
    assertNotNull(emprunt);
}
```

### ✅ Flexibilité
- Changer H2 → PostgreSQL : modifier uniquement application.properties
- Ajouter GraphQL : créer un nouveau adapter, le domain reste intact
- Remplacer JPA par autre chose : modifier uniquement la couche adapters

### ✅ Maintenabilité
- Chaque couche a une responsabilité claire
- Facile de trouver où faire un changement
- Séparation des préoccupations

## 📂 Mapping des packages

```
src/main/java/com/bibliotheque/gestion_bibliotheque/
│
├── domain/                              [COUCHE 1]
│   ├── entities/
│   │   ├── Livre.java
│   │   ├── Membre.java
│   │   ├── Emprunt.java
│   │   └── Reservation.java
│   └── repository/
│       ├── LivreRepository.java         (interface)
│       ├── MembreRepository.java        (interface)
│       └── ...
│
├── application/                         [COUCHE 2]
│   ├── service/
│   │   ├── LivreService.java
│   │   ├── MembreService.java
│   │   ├── EmpruntService.java
│   │   ├── ReservationService.java
│   │   └── StatistiqueService.java
│   ├── dto/
│   │   ├── LivreDto.java
│   │   ├── MembreDto.java
│   │   └── ...
│   └── mapper/
│       ├── LivreMapper.java
│       └── ...
│
└── adapters/                            [COUCHE 3]
    ├── controller/
    │   ├── LivreController.java
    │   ├── MembreController.java
    │   ├── EmpruntController.java
    │   ├── ReservationController.java
    │   └── StatistiqueController.java
    ├── repository/
    │   ├── LivreRepositoryAdapter.java  (implémentation)
    │   └── ...
    └── infrastructure/
        ├── entity/
        │   ├── LivreEntity.java         (JPA)
        │   └── ...
        └── repository/
            ├── JpaLivreRepository.java  (Spring Data)
            └── ...
```

## 🔍 Règle de dépendance

**Règle d'or** : Les dépendances pointent toujours vers l'intérieur

```
Frameworks & Drivers
        ↓
   Adapters
        ↓
   Application
        ↓
     Domain
```

- Domain ne dépend de RIEN
- Application dépend de Domain
- Adapters dépend de Application et Domain
- Frameworks & Drivers dépend de tout

## 📚 Ressources

- Clean Architecture (livre de Robert C. Martin)
- Cours d'Architecture d'Application - ESIEA
- Documentation Spring Boot : https://spring.io/projects/spring-boot

---

**Auteurs** : Messai, Ramanadane, Ouallii - ESIEA Master 1 - 2026
