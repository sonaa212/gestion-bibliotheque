# 👥 RÉPARTITION DU TRAVAIL

**Projet** : Système de Gestion de Bibliothèque  
**Équipe** : Messai, Ramanadane, Ouallii  
**Formation** : ESIEA - Master 1 - Architecture d'Application  
**Année** : 2025-2026

---

## 📊 Répartition Générale

| Membre | Use Cases | Endpoints | Composants | Temps estimé |
|--------|-----------|-----------|------------|--------------|
| **Messai** | 2+ | 4+ | Livres + Réservations | 33% |
| **Ramanadane** | 2+ | 4+ | Membres + Emprunts | 33% |
| **Ouallii** | 2+ | 4+ | Kafka + Statistiques + Docker | 34% |

---

## 👤 MESSAI - Gestion des Livres & Réservations

### Use Cases Développés
1. **Ajouter un livre** - Création d'un nouveau livre dans le catalogue
2. **Rechercher des livres** - Recherche par titre, auteur, ISBN, catégorie
3. **Réserver un livre** - Créer une réservation pour un livre non disponible
4. **Gérer la file d'attente** - Notifier les réservations en attente

### Endpoints REST
- `POST /api/livres` - Ajouter un livre
- `GET /api/livres` - Lister tous les livres
- `GET /api/livres/{id}` - Obtenir un livre par ID
- `GET /api/livres/recherche` - Rechercher des livres
- `PUT /api/livres/{id}` - Modifier un livre
- `DELETE /api/livres/{id}` - Supprimer un livre
- `POST /api/reservations` - Créer une réservation
- `GET /api/reservations` - Lister les réservations
- `GET /api/reservations/membre/{membreId}` - Réservations d'un membre

### Fichiers Créés/Modifiés
```
domain/
  ├── entities/Livre.java
  └── entities/Reservation.java
  └── repository/LivreRepository.java
  └── repository/ReservationRepository.java

application/
  ├── dto/LivreDto.java
  ├── dto/ReservationDto.java
  ├── mapper/LivreMapper.java
  ├── mapper/ReservationMapper.java
  ├── service/LivreService.java
  └── service/ReservationService.java

adapters/
  ├── controller/LivreController.java
  ├── controller/ReservationController.java
  ├── repository/LivreRepositoryAdapter.java
  ├── repository/ReservationRepositoryAdapter.java
  ├── infrastructure/entity/LivreEntity.java
  └── infrastructure/entity/ReservationEntity.java
```

---

## 👤 RAMANADANE - Gestion des Membres & Emprunts

### Use Cases Développés
1. **Inscrire un membre** - Créer un nouveau compte membre
2. **Gérer les quotas** - Vérifier et appliquer les quotas selon le type
3. **Emprunter un livre** - Créer un emprunt avec vérifications
4. **Retourner un livre** - Traiter le retour avec calcul des pénalités

### Endpoints REST
- `POST /api/membres` - Inscrire un nouveau membre
- `GET /api/membres` - Lister tous les membres
- `GET /api/membres/{id}` - Obtenir un membre par ID
- `PUT /api/membres/{id}` - Modifier un membre
- `DELETE /api/membres/{id}` - Supprimer un membre
- `POST /api/emprunts` - Emprunter un livre
- `GET /api/emprunts` - Lister tous les emprunts
- `GET /api/emprunts/membre/{membreId}` - Emprunts d'un membre
- `PUT /api/emprunts/{id}/retourner` - Retourner un livre

### Fichiers Créés/Modifiés
```
domain/
  ├── entities/Membre.java
  ├── entities/Emprunt.java
  ├── repository/MembreRepository.java
  └── repository/EmpruntRepository.java

application/
  ├── dto/MembreDto.java
  ├── dto/EmpruntDto.java
  ├── mapper/MembreMapper.java
  ├── mapper/EmpruntMapper.java
  ├── service/MembreService.java
  └── service/EmpruntService.java

adapters/
  ├── controller/MembreController.java
  ├── controller/EmpruntController.java
  ├── repository/MembreRepositoryAdapter.java
  ├── repository/EmpruntRepositoryAdapter.java
  ├── infrastructure/entity/MembreEntity.java
  └── infrastructure/entity/EmpruntEntity.java
```

---

## 👤 OUALLII - Kafka, Statistiques & Infrastructure

### Use Cases Développés
1. **Publier événement Emprunt** - Production Kafka lors d'un emprunt
2. **Consommer événement Emprunt** - Traitement asynchrone des emprunts
3. **Générer statistiques** - Calculer les KPIs de la bibliothèque
4. **Gérer les top livres** - Identifier les livres les plus empruntés

### Endpoints REST
- `GET /api/statistiques/dashboard` - Tableau de bord général
- `GET /api/statistiques/top-livres` - Top 5 livres les plus empruntés
- `GET /api/statistiques/categorie/{categorie}` - Stats par catégorie
- `GET /api/statistiques/taux-retard` - Taux de retard global

### Architecture Événementielle (Kafka)
```
adapters/
  └── messaging/
      ├── event/EmpruntCreeEvent.java
      ├── producer/EmpruntEventProducer.java
      └── consumer/EmpruntEventConsumer.java
```

### Infrastructure & Configuration
```
config/
  ├── KafkaConfig.java
  ├── SwaggerConfig.java
  └── DataInitializer.java

application.properties
docker-compose-kafka.yml (initial)
docker-compose.yml (complet)
Dockerfile
.dockerignore
```

### Documentation & Déploiement
- Configuration Kafka dans `application.properties`
- Création du `docker-compose.yml` complet
- Création du `Dockerfile`
- Scripts de déploiement Docker
- Tests et validation Kafka

### Fichiers Créés/Modifiés
```
application/
  ├── dto/StatistiqueDto.java
  ├── service/StatistiqueService.java

adapters/
  ├── controller/StatistiqueController.java
  ├── messaging/
  │   ├── event/EmpruntCreeEvent.java
  │   ├── producer/EmpruntEventProducer.java
  │   └── consumer/EmpruntEventConsumer.java

config/
  ├── KafkaConfig.java
  ├── SwaggerConfig.java
  └── DataInitializer.java

infrastructure/
  ├── Dockerfile
  ├── docker-compose.yml
  ├── docker-compose-kafka.yml
  └── .dockerignore
```

---

## 🤝 Travail Collaboratif

### Parties Communes
- **Architecture globale** - Décision collective
- **Modélisation domaine** - Design collaboratif
- **Gestion des exceptions** - GlobalExceptionHandler (commun)
- **Documentation** - RAPPORT.md, README.md, ARCHITECTURE.md (contribution collective)
- **Tests manuels** - Validation croisée des fonctionnalités

### Communication & Coordination
- Réunions régulières pour synchronisation
- Revue de code mutuelle
- Tests d'intégration entre modules
- Documentation partagée

---

## 📈 Statistiques du Projet

### Lignes de Code (estimation)
| Membre | Java | Config | Total |
|--------|------|--------|-------|
| Messai | ~800 | ~50 | ~850 |
| Ramanadane | ~900 | ~50 | ~950 |
| Ouallii | ~600 | ~300 | ~900 |
| **Total** | **~2300** | **~400** | **~2700** |

### Commits (à documenter via Git)
```bash
# Vérifier les contributions
git log --author="Messai" --oneline | wc -l
git log --author="Ramanadane" --oneline | wc -l
git log --author="Ouallii" --oneline | wc -l
```

---

## ✅ Validation

Chaque membre a :
- ✅ Développé au moins **2 use cases** complets
- ✅ Créé au moins **4 endpoints REST** fonctionnels
- ✅ Respecté la Clean Architecture
- ✅ Documenté son code
- ✅ Testé ses fonctionnalités

---

## 📝 Signatures

| Membre | Signature | Date |
|--------|-----------|------|
| Messai | ___________ | 09/01/2026 |
| Ramanadane | ___________ | 09/01/2026 |
| Ouallii | ___________ | 09/01/2026 |

---

*Document généré le 09 Janvier 2026*
