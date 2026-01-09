# 🚀 GUIDE DE DÉPLOIEMENT DOCKER

**Projet** : Système de Gestion de Bibliothèque  
**Date** : Janvier 2026

---

## 📋 Prérequis

- Docker Desktop installé et démarré
- Maven 3.8+
- Java 17+
- 4 GB de RAM disponible
- Ports libres : 8080, 9092, 2181

---

## 🔧 Étape 1 : Compiler le Projet

```bash
# Se placer dans le répertoire du projet
cd "d:\étude\école\Projet architecture d applicatoin MAJEUR"

# Compiler avec Maven (génère le JAR dans target/)
./mvnw clean package -DskipTests

# Vérifier que le JAR existe
ls target/gestion-bibliotheque-0.0.1-SNAPSHOT.jar
```

---

## 🐳 Étape 2 : Construire l'Image Docker

```bash
# Construire l'image Docker
docker build -t bibliotheque-app:latest .

# Vérifier que l'image est créée
docker images | grep bibliotheque-app
```

**Résultat attendu :**
```
REPOSITORY          TAG       IMAGE ID       CREATED         SIZE
bibliotheque-app    latest    abc123def456   2 minutes ago   450MB
```

---

## 🚀 Étape 3 : Démarrer avec Docker Compose

```bash
# Démarrer tous les services (Zookeeper, Kafka, Backend)
docker-compose up -d

# Vérifier que tous les conteneurs sont démarrés
docker-compose ps
```

**Résultat attendu :**
```
NAME                        STATUS              PORTS
bibliotheque-backend        Up (healthy)        0.0.0.0:8080->8080/tcp
bibliotheque-kafka          Up (healthy)        0.0.0.0:9092->9092/tcp
bibliotheque-zookeeper      Up (healthy)        0.0.0.0:2181->2181/tcp
```

---

## 📊 Étape 4 : Vérifier le Déploiement

### 4.1 Vérifier les logs du backend

```bash
# Voir les logs de l'application
docker-compose logs -f backend
```

**Logs attendus :**
```
Started GestionBibliothequeApplication in X seconds
Tomcat started on port(s): 8080
```

### 4.2 Vérifier que l'API est accessible

```bash
# Test de l'API (PowerShell)
curl http://localhost:8080/swagger-ui.html

# Ou ouvrir dans le navigateur
start http://localhost:8080/swagger-ui.html
```

### 4.3 Vérifier Kafka

```bash
# Logs Kafka
docker-compose logs kafka | grep "started"

# Entrer dans le conteneur Kafka
docker exec -it bibliotheque-kafka bash

# Lister les topics
kafka-topics --list --bootstrap-server localhost:9092

# Sortir
exit
```

---

## 🧪 Étape 5 : Tester l'Application

### Test 1 : Créer un livre via l'API

```bash
# PowerShell
Invoke-WebRequest -Uri "http://localhost:8080/api/livres" -Method POST -Headers @{"Content-Type"="application/json"} -Body '{"titre":"Test Docker","auteur":"Docker Team","isbn":"ISBN-DOCKER-001","editeur":"Docker Pub","anneePublication":2026,"categorie":"INFORMATIQUE","nombreExemplaires":5,"etatPhysique":"NEUF"}'
```

### Test 2 : Emprunter un livre (déclenche Kafka)

```bash
# PowerShell
Invoke-WebRequest -Uri "http://localhost:8080/api/emprunts?livreId=1&membreId=1" -Method POST
```

### Test 3 : Vérifier l'événement Kafka

```bash
# Voir les logs du consumer
docker-compose logs backend | grep "Événement Kafka reçu"
```

**Résultat attendu :**
```
=== Événement Kafka reçu ===
Traitement de l'événement EmpruntCree:
  - Emprunt ID: 1
  - Livre: Clean Architecture (ID: 1)
  - Membre: Dupont Jean (ID: 1)
✓ Événement traité avec succès
```

---

## 📸 Captures d'Écran Requises

### Capture 1 : docker-compose ps
```bash
docker-compose ps
# Faire une capture d'écran
```

### Capture 2 : API Swagger accessible
- Ouvrir http://localhost:8080/swagger-ui.html
- Faire une capture de la page Swagger

### Capture 3 : Logs Kafka
```bash
docker-compose logs backend | grep "Événement"
# Faire une capture montrant les événements Kafka
```

### Capture 4 : Test d'emprunt
- Dans Swagger, tester POST /api/emprunts
- Faire une capture de la réponse 200 OK

---

## 🛑 Arrêter les Services

```bash
# Arrêter tous les conteneurs
docker-compose down

# Arrêter et supprimer les volumes
docker-compose down -v
```

---

## 🔍 Dépannage

### Problème 1 : Le backend ne démarre pas

```bash
# Vérifier les logs
docker-compose logs backend

# Possible cause : Kafka pas prêt
# Solution : Attendre 30-60 secondes
```

### Problème 2 : Erreur "Address already in use"

```bash
# Trouver quel processus utilise le port 8080
netstat -ano | findstr :8080

# Arrêter le processus ou changer le port dans docker-compose.yml
```

### Problème 3 : Le JAR n'existe pas

```bash
# Recompiler
./mvnw clean package -DskipTests

# Vérifier
ls target/*.jar
```

### Problème 4 : Docker Desktop n'est pas démarré

```
unable to get image: error during connect: open //./pipe/dockerDesktopLinuxEngine
```

**Solution** : Démarrer Docker Desktop et attendre qu'il soit prêt

---

## 📝 Checklist de Validation

- [ ] Projet compilé (JAR existe)
- [ ] Image Docker construite
- [ ] docker-compose up réussi
- [ ] 3 conteneurs en état "healthy"
- [ ] API accessible sur http://localhost:8080
- [ ] Swagger accessible sur http://localhost:8080/swagger-ui.html
- [ ] Kafka producteur fonctionne (événement envoyé)
- [ ] Kafka consumer fonctionne (événement reçu et traité)
- [ ] Captures d'écran prises

---

## 📚 Commandes Utiles

```bash
# Voir tous les conteneurs
docker ps -a

# Voir les logs en temps réel
docker-compose logs -f

# Redémarrer un service spécifique
docker-compose restart backend

# Entrer dans le conteneur backend
docker exec -it bibliotheque-backend bash

# Vérifier la configuration réseau
docker network inspect bibliotheque-network

# Nettoyer complètement
docker-compose down -v --rmi all
```

---

## ✅ Validation Finale

Exécuter ce script pour valider le déploiement :

```bash
# test-deployment.ps1
Write-Host "🧪 Test de déploiement Docker..." -ForegroundColor Cyan

# 1. Vérifier les conteneurs
Write-Host "`n1. Vérification des conteneurs..." -ForegroundColor Yellow
docker-compose ps

# 2. Test API Health
Write-Host "`n2. Test de l'API..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/swagger-ui.html" -UseBasicParsing
    Write-Host "✅ API accessible (Status: $($response.StatusCode))" -ForegroundColor Green
} catch {
    Write-Host "❌ API non accessible" -ForegroundColor Red
}

# 3. Test Kafka
Write-Host "`n3. Vérification Kafka..." -ForegroundColor Yellow
docker exec bibliotheque-kafka kafka-topics --list --bootstrap-server localhost:9092

Write-Host "`n✅ Tests terminés!" -ForegroundColor Cyan
```

---

*Guide créé le 09/01/2026*
