# Rapport de Projet - Système de Gestion de Bibliothèque

**Projet d'Architecture d'Application**  
**ESIEA - Master 1**  
**Année 2025-2026**

---

## 👥 Équipe de Développement

- **Messai**
- **Ramanadane**
- **Ouallii**

---

## 📋 Table des Matières

1. [Introduction](#introduction)
2. [Contexte et Objectifs](#contexte-et-objectifs)
3. [Architecture Fonctionnelle](#architecture-fonctionnelle)
4. [Architecture Applicative](#architecture-applicative)
5. [Architecture Technique](#architecture-technique)
6. [Fonctionnalités Implémentées](#fonctionnalités-implémentées)
7. [Guide d'Utilisation](#guide-dutilisation)
8. [Tests et Validation](#tests-et-validation)
9. [Difficultés Rencontrées](#difficultés-rencontrées)
10. [Améliorations Futures](#améliorations-futures)
11. [Conclusion](#conclusion)

---

## 1. Introduction

Ce projet consiste en la réalisation d'un système de gestion de bibliothèque utilisant les principes de la **Clean Architecture**. L'application permet de gérer un catalogue de livres, les membres de la bibliothèque, les emprunts, les réservations et fournit des statistiques d'utilisation.

Le projet a été développé avec **Spring Boot** et utilise une base de données **H2** pour le développement.

---

## 2. Contexte et Objectifs

### Contexte
Les bibliothèques modernes ont besoin d'outils numériques pour gérer efficacement leurs collections et leurs utilisateurs. Ce projet répond à ce besoin en proposant une solution complète et évolutive.

### Objectifs
- Apprendre et appliquer les principes de la Clean Architecture
- Développer une API REST bien structurée
- Implémenter des use cases métier complexes
- Documenter l'architecture et le code
- Utiliser les frameworks modernes (Spring Boot, JPA)

---

## 3. Architecture Fonctionnelle

### Blocs Fonctionnels Principaux

#### 1. Gestion des Livres
- Ajout, modification, suppression de livres
- Calcul automatique de la disponibilité
- Gestion des catégories et des stocks
- Suivi de l'état physique (neuf, bon état, abîmé, perdu)

#### 2. Gestion des Membres
- Inscription et authentification
- Gestion des profils utilisateurs
- Attribution de quotas selon le type de membre
  - Étudiant : 5 livres
  - Enseignant : 10 livres
  - Personnel : 7 livres
- Système de score de fiabilité

#### 3. Gestion des Emprunts
- Création d'emprunts avec calcul automatique de date de retour
- Suivi des emprunts en cours et en retard
- Calcul automatique des pénalités (1€/jour)
- Mise à jour du score selon les retours

#### 4. Gestion des Réservations
- Réserver un livre non disponible
- File d'attente automatique
- Notification de disponibilité
- Délai de retrait de 3 jours

#### 5. Statistiques et Rapports
- Tableau de bord général
- Top 5 des livres les plus empruntés
- Statistiques par catégorie
- Taux de retard global

### Diagramme de Flux

```
Utilisateur → Recherche Livre → Disponible ?
                                    ├─ Oui → Emprunter
                                    └─ Non → Réserver → File d'attente
                                    
Retour Livre → Calcul pénalités → Mise à jour score → Notification réservation
```

---

## 4. Architecture Applicative

Nous avons appliqué les principes de la **Clean Architecture** de Robert C. Martin.

### Structure en 4 Couches

#### Couche 1 : Domain (Entités)
- `Livre`, `Membre`, `Emprunt`, `Reservation`
- Logique métier pure
- Aucune dépendance externe

#### Couche 2 : Application (Use Cases)
- Services métier
- Orchestration des cas d'utilisation
- DTOs et Mappers

#### Couche 3 : Adapters (Interface)
- Controllers REST
- Repository Adapters
- Entités JPA

#### Couche 4 : Frameworks & Drivers
- Spring Boot
- Spring Data JPA
- H2 Database
- Swagger

### Avantages de cette Architecture
- **Testabilité** : Chaque couche testable indépendamment
- **Maintenabilité** : Séparation claire des responsabilités
- **Évolutivité** : Facile d'ajouter de nouvelles fonctionnalités
- **Indépendance** : Le métier ne dépend pas des frameworks

---

## 5. Architecture Technique

### Stack Technologique

| Composant | Technologie | Version | Rôle |
|-----------|-------------|---------|------|
| Backend | Spring Boot | 2.7.18 | Framework Java |
| Langage | Java | 17 | Langage de programmation |
| ORM | Hibernate | 5.6+ | Mapping objet-relationnel |
| Base de données | H2 | 2.2+ | Base de données en mémoire |
| API Docs | Springdoc OpenAPI | 1.7.0 | Documentation automatique |
| Build Tool | Maven | 3.8+ | Gestion des dépendances |

### Endpoints API REST

```
Livres:          /api/livres
Membres:         /api/membres
Emprunts:        /api/emprunts
Réservations:    /api/reservations
Statistiques:    /api/statistiques
```

### Base de Données

Tables automatiquement créées par JPA :
- `livres` - Catalogue des livres
- `membres` - Liste des membres
- `emprunts` - Historique des emprunts
- `reservations` - File d'attente des réservations

---

## 6. Fonctionnalités Implémentées

### ✅ Fonctionnalités Complètes

1. **CRUD Complet**
   - Livres : Create, Read, Update, Delete
   - Membres : Create, Read, Update, Delete
   - Recherche avancée (titre, auteur, ISBN, catégorie)

2. **Logique Métier**
   - Vérification des quotas d'emprunt
   - Calcul automatique des dates de retour
   - Calcul des pénalités de retard
   - Mise à jour du score de fiabilité

3. **Gestion des Réservations**
   - File d'attente automatique
   - Gestion des positions
   - Notifications de disponibilité
   - Expiration automatique

4. **Statistiques**
   - Dashboard général
   - Top livres empruntés
   - Statistiques par catégorie
   - Taux de retard

### 📊 Exemple de Données Pré-chargées

Au démarrage, l'application charge automatiquement :
- 5 livres de test (Clean Architecture, Design Patterns, etc.)
- 3 membres de test (étudiant, enseignant, personnel)

---

## 7. Guide d'Utilisation

### Démarrage de l'Application

```bash
# 1. Cloner le projet
git clone [URL_DU_PROJET]

# 2. Compiler
./mvnw clean install

# 3. Lancer
./mvnw spring-boot:run
```

### Accès aux Interfaces

- **API** : http://localhost:8080
- **Swagger UI** : http://localhost:8080/swagger-ui.html
- **Console H2** : http://localhost:8080/h2-console

### Exemples d'Utilisation

#### Emprunter un livre
```http
POST /api/emprunts?livreId=1&membreId=1
```

#### Réserver un livre
```http
POST /api/reservations?livreId=1&membreId=2
```

#### Voir le tableau de bord
```http
GET /api/statistiques/dashboard
```

---

## 8. Tests et Validation

### Tests Manuels via Swagger

Tous les endpoints ont été testés manuellement via l'interface Swagger :
- ✅ Création de livres et membres
- ✅ Emprunts et retours
- ✅ Réservations et file d'attente
- ✅ Calcul des pénalités
- ✅ Mise à jour des scores
- ✅ Statistiques

### Scénarios de Test Validés

1. **Scénario 1** : Emprunt standard
   - Créer un membre
   - Emprunter un livre
   - Retourner à temps
   - Score augmente de 5 points

2. **Scénario 2** : Retard
   - Emprunter un livre
   - Retourner en retard
   - Pénalité calculée
   - Score diminue de 10 points

3. **Scénario 3** : Réservation
   - Emprunter tous les exemplaires
   - Réserver le livre
   - Retourner un exemplaire
   - Première réservation notifiée

---

## 9. Difficultés Rencontrées

### Problèmes Techniques

1. **Organisation des packages**
   - Solution : Respecter strictement la Clean Architecture
   - Bien séparer domain, application, adapters

2. **Gestion des dépendances circulaires**
   - Problème : EmpruntService → ReservationService → LivreService
   - Solution : Injection de dépendances Spring

3. **Mappage Entity ↔ Domain**
   - Multiplication des classes (Entity, Domain, DTO)
   - Solution : Mappers dédiés, code clair

### Apprentissages

- Importance de la séparation des couches
- Avantage de l'injection de dépendances
- Utilité de la documentation automatique (Swagger)

---

## 10. Améliorations Futures

### Court Terme
- [ ] Tests unitaires et d'intégration
- [ ] Gestion des exceptions globale
- [ ] Validation des données d'entrée
- [ ] Pagination des résultats

### Moyen Terme
- [ ] Authentification JWT
- [ ] Système de notifications par email
- [ ] Interface frontend (React/Vue.js)
- [ ] Migration vers PostgreSQL

### Long Terme
- [ ] Gestion des amendes et paiements
- [ ] Système de recommandations de livres
- [ ] Application mobile
- [ ] Intégration avec d'autres bibliothèques

---

## 11. Conclusion

Ce projet nous a permis de :
- ✅ Comprendre et appliquer la Clean Architecture
- ✅ Développer une API REST complète
- ✅ Utiliser Spring Boot et JPA
- ✅ Gérer un projet en équipe
- ✅ Documenter notre code et notre architecture

Le système de gestion de bibliothèque est fonctionnel et prêt à être étendu. L'architecture choisie facilite l'ajout de nouvelles fonctionnalités et la maintenance du code.

### Points Forts du Projet
- Architecture claire et bien structurée
- Code commenté et documenté
- Fonctionnalités principales complètes
- Documentation technique détaillée

### Ce que nous avons appris
- L'importance de l'architecture logicielle
- La valeur de la séparation des responsabilités
- L'utilité des design patterns
- Le travail en équipe sur un projet structuré

---

## 📚 Références

- Clean Architecture - Robert C. Martin
- Spring Boot Documentation
- Documentation du cours d'Architecture d'Application - ESIEA
- Patterns of Enterprise Application Architecture - Martin Fowler

---

**Date de rendu** : Janvier 2026  
**Encadrant** : Kawtar LAHMINI  
**Établissement** : ESIEA Paris

---

## Annexes

### A. Structure Complète du Projet

Voir fichier `ARCHITECTURE.md`

### B. Guide d'Installation

Voir fichier `README.md`

### C. Endpoints API

Voir documentation Swagger : `/swagger-ui.html`

---

*Fin du rapport*
