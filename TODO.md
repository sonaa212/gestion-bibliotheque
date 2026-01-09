# TODO - Améliorations du Projet

## 🔴 Priorité Haute (À faire rapidement)

### Tests
- [ ] Ajouter des tests unitaires pour les services
  - [ ] LivreService
  - [ ] MembreService
  - [ ] EmpruntService
  - [ ] ReservationService
- [ ] Ajouter des tests d'intégration pour les controllers
- [ ] Augmenter la couverture de code à 80%+

### Validation
- [ ] Ajouter les annotations de validation (@NotNull, @Size, etc.)
- [ ] Créer un gestionnaire d'exceptions global (@ControllerAdvice)
- [ ] Valider les données d'entrée dans les DTOs

### Sécurité
- [ ] Corriger les warnings "Null type safety" dans les repository adapters
- [ ] Ajouter la gestion des erreurs 404/400/500

## 🟡 Priorité Moyenne (Nice to have)

### API
- [ ] Ajouter la pagination sur les listes
  - [ ] GET /api/livres?page=0&size=10
  - [ ] GET /api/membres?page=0&size=10
- [ ] Ajouter le tri sur les endpoints
  - [ ] GET /api/livres?sort=titre,asc
- [ ] Ajouter des filtres avancés

### Fonctionnalités
- [ ] Système de prolongation d'emprunt
  - [ ] Limité à 1 prolongation par emprunt
  - [ ] Prolongation de 7 jours
- [ ] Améliorer le système de pénalités
  - [ ] Paliers de pénalités selon le score
  - [ ] Blocage des emprunts si trop de retard
- [ ] Ajouter un système de commentaires/notes sur les livres

### Documentation
- [ ] Compléter la documentation Swagger des DTOs
- [ ] Ajouter des exemples de requêtes/réponses
- [ ] Créer un Postman Collection

## 🟢 Priorité Basse (Pour plus tard)

### Authentification
- [ ] Implémenter Spring Security
- [ ] Ajouter JWT pour l'authentification
- [ ] Gérer les rôles (MEMBRE, BIBLIOTHECAIRE, ADMIN)
- [ ] Sécuriser les endpoints selon les rôles

### Notifications
- [ ] Configurer Spring Mail
- [ ] Envoyer des emails de rappel 3 jours avant
- [ ] Envoyer des alertes de retard
- [ ] Notifier les réservations disponibles

### Performance
- [ ] Ajouter du caching (Spring Cache)
- [ ] Optimiser les requêtes JPA (éviter N+1)
- [ ] Ajouter des index sur la base de données

### Interface Utilisateur
- [ ] Créer un frontend simple
  - [ ] React ou Vue.js
  - [ ] Pages : catalogue, mes emprunts, mes réservations
- [ ] Ajouter un dashboard admin

### Base de Données
- [ ] Migrer vers PostgreSQL pour la production
- [ ] Ajouter Flyway pour les migrations
- [ ] Créer des scripts de backup

### DevOps
- [ ] Créer un Dockerfile
- [ ] Ajouter docker-compose (app + PostgreSQL)
- [ ] Configurer une CI/CD (GitHub Actions / GitLab CI)
- [ ] Déployer sur le cloud (Azure / AWS)

## 📝 Bugs Connus

- [ ] Les warnings "Null type safety" dans les repository adapters (non bloquant)
- [ ] Pas de gestion des exemplaires multiples lors de la réservation
- [ ] Le système ne vérifie pas si un membre a déjà une réservation pour le même livre

## 💡 Idées d'Améliorations

### Fonctionnalités Avancées
- [ ] Système de recommandation de livres
  - Basé sur l'historique d'emprunts
  - Suggestions par catégorie
- [ ] Gestion des amendes
  - Calcul automatique
  - Paiement en ligne
- [ ] Import/Export de données
  - CSV pour les livres
  - Excel pour les statistiques
- [ ] API de recherche avancée
  - Recherche full-text
  - Filtres combinés (auteur + catégorie + année)
- [ ] Wishlist de livres
  - Les membres peuvent suggérer des livres à acheter
  - Vote sur les suggestions

### Reporting
- [ ] Générer des rapports PDF
- [ ] Graphiques de statistiques (Chart.js)
- [ ] Rapport mensuel automatique par email

### Mobile
- [ ] Application mobile (React Native / Flutter)
- [ ] Scanner de code-barres pour ISBN
- [ ] Notifications push

## ✅ Terminé

- [x] Structure Clean Architecture
- [x] CRUD Livres
- [x] CRUD Membres
- [x] Gestion des Emprunts
- [x] Gestion des Réservations
- [x] Système de score
- [x] Calcul des pénalités
- [x] Statistiques basiques
- [x] Documentation Swagger
- [x] README complet
- [x] Documentation architecture
- [x] Rapport de projet
- [x] Données de test au démarrage

---

## 📊 Statistiques du Projet

- **Entités Domain** : 4 (Livre, Membre, Emprunt, Reservation)
- **Services** : 5 (Livre, Membre, Emprunt, Reservation, Statistique)
- **Controllers** : 5 (Livre, Membre, Emprunt, Reservation, Statistique)
- **Endpoints API** : ~35
- **Lignes de code** : ~2500

---

*Dernière mise à jour : Janvier 2026*
