# 🚀 GUIDE D'UTILISATION - Système de Gestion de Bibliothèque

## 📋 Démarrage rapide

### 1️⃣ Démarrer l'application
```powershell
cd "d:\étude\école\Projet architecture d applicatoin MAJEUR"
.\mvnw.cmd spring-boot:run -DskipTests
```

**L'application démarre sur :** http://localhost:8080  
**Temps de démarrage :** ~5-7 secondes

---

## 🌐 Interfaces disponibles

| Interface | URL | Description |
|-----------|-----|-------------|
| **Swagger UI** | http://localhost:8080/swagger-ui.html | Documentation API interactive |
| **API Docs** | http://localhost:8080/v3/api-docs | Spécification OpenAPI JSON |
| **H2 Console** | http://localhost:8080/h2-console | Console base de données |

### Configuration H2 Console :
- **JDBC URL :** `jdbc:h2:mem:bibliotheque`
- **Username :** `sa`
- **Password :** *(laisser vide)*

---

## 📚 Endpoints REST principaux

### **Livres** (`/api/livres`)
```http
GET    /api/livres              # Liste tous les livres
GET    /api/livres/{id}         # Détails d'un livre
GET    /api/livres/disponibles  # Livres disponibles
POST   /api/livres              # Créer un livre
PUT    /api/livres/{id}         # Modifier un livre
DELETE /api/livres/{id}         # Supprimer un livre
```

### **Membres** (`/api/membres`)
```http
GET    /api/membres         # Liste tous les membres
GET    /api/membres/{id}    # Détails d'un membre
POST   /api/membres         # Créer un membre
PUT    /api/membres/{id}    # Modifier un membre
DELETE /api/membres/{id}    # Supprimer un membre
```

### **Emprunts** (`/api/emprunts`)
```http
GET    /api/emprunts                    # Liste tous les emprunts
GET    /api/emprunts/{id}               # Détails d'un emprunt
POST   /api/emprunts?livreId=1&membreId=1  # Créer un emprunt
PUT    /api/emprunts/{id}/retour        # Retourner un livre
GET    /api/emprunts/membre/{membreId}  # Emprunts d'un membre
GET    /api/emprunts/livre/{livreId}    # Emprunts d'un livre
GET    /api/emprunts/en-cours           # Emprunts actifs
```

### **Réservations** (`/api/reservations`)
```http
GET    /api/reservations                      # Liste toutes les réservations
GET    /api/reservations/{id}                 # Détails d'une réservation
POST   /api/reservations?livreId=1&membreId=1 # Créer une réservation
DELETE /api/reservations/{id}                 # Annuler une réservation
GET    /api/reservations/membre/{membreId}    # Réservations d'un membre
```

### **Statistiques** (`/api/statistiques`)
```http
GET /api/statistiques  # Statistiques globales de la bibliothèque
```

---

## 🧪 Exemples de requêtes

### **Créer un nouveau livre**
```bash
curl -X POST http://localhost:8080/api/livres \
  -H "Content-Type: application/json" \
  -d '{
    "titre": "Le Petit Prince",
    "auteur": "Antoine de Saint-Exupéry",
    "isbn": "978-2-07-061275-8",
    "anneePublication": 1943,
    "categorie": "Philosophie",
    "editeur": "Gallimard",
    "nombreExemplaires": 5,
    "nombreDisponibles": 5,
    "etatPhysique": "BON"
  }'
```

### **Créer un nouveau membre**
```bash
curl -X POST http://localhost:8080/api/membres \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Martin",
    "prenom": "Sophie",
    "email": "sophie.martin@example.com",
    "typeMembre": "STANDARD",
    "quotaEmprunt": 3,
    "scoreFiabilite": 100
  }'
```

### **Emprunter un livre**
```bash
curl -X POST "http://localhost:8080/api/emprunts?livreId=1&membreId=1"
```

### **Retourner un livre**
```bash
curl -X PUT http://localhost:8080/api/emprunts/1/retour
```

### **Consulter les statistiques**
```bash
curl http://localhost:8080/api/statistiques
```

---

## 🔧 Commandes Maven utiles

### **Compilation**
```powershell
.\mvnw.cmd clean compile
```

### **Tests**
```powershell
.\mvnw.cmd test
```

### **Package (création JAR)**
```powershell
.\mvnw.cmd clean package -DskipTests
```

### **Exécuter le JAR**
```powershell
java -jar target/gestion-bibliotheque-0.0.1-SNAPSHOT.jar
```

---

## 🐳 Activer Kafka (Architecture Événementielle)

### **1. Démarrer Kafka avec Docker**
```powershell
# Zookeeper
docker run -d --name zookeeper -p 2181:2181 wurstmeister/zookeeper

# Kafka
docker run -d --name kafka -p 9092:9092 `
  -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 `
  -e KAFKA_ZOOKEEPER_CONNECT=host.docker.internal:2181 `
  wurstmeister/kafka
```

### **2. Modifier application.properties**
```properties
spring.kafka.enabled=true  # Changer de false à true
```

### **3. Redémarrer l'application**
```powershell
.\mvnw.cmd spring-boot:run
```

### **4. Tester les événements**
Créez un emprunt, l'événement sera publié sur `emprunts-topic` :
```bash
curl -X POST "http://localhost:8080/api/emprunts?livreId=1&membreId=1"
```

Vérifiez les logs pour voir :
```
✅ Événement publié: Emprunt créé - ID: 1
✅ Événement reçu: Emprunt 1
```

---

## 📊 Données de test préchargées

### **Livres disponibles au démarrage :**
1. **Les Misérables** - Victor Hugo (ISBN: 978-2-07-036660-8)
2. **Harry Potter à l'école des sorciers** - J.K. Rowling (978-2-07-054120-7)
3. **1984** - George Orwell (978-0-452-28423-4)
4. **Le Seigneur des Anneaux** - J.R.R. Tolkien (978-2-266-15410-5)
5. **L'Étranger** - Albert Camus (978-2-07-036002-6)

### **Membres créés :**
1. **Jean Dupont** - jean.dupont@example.com
2. **Marie Martin** - marie.martin@example.com
3. **Pierre Dubois** - pierre.dubois@example.com

---

## ❌ Arrêter l'application

Dans le terminal où l'application tourne :
```
Ctrl + C
```

Ou depuis PowerShell :
```powershell
# Trouver le processus
Get-Process -Name java | Where-Object {$_.Path -like "*gestion-bibliotheque*"}

# Arrêter le processus
Stop-Process -Name java -Force
```

---

## 🐛 Dépannage

### **Port 8080 déjà utilisé**
Changez le port dans `application.properties` :
```properties
server.port=8081
```

### **Erreur de compilation**
```powershell
.\mvnw.cmd clean compile
```

### **Base H2 corrompue**
Supprimez le dossier `target/` et redémarrez :
```powershell
Remove-Item -Recurse -Force target/
.\mvnw.cmd spring-boot:run
```

### **Maven Wrapper ne fonctionne pas**
Utilisez Maven directement :
```powershell
mvn spring-boot:run
```

---

## 📚 Documentation complémentaire

- **Rapport de conformité :** [RAPPORT_CONFORMITE.md](RAPPORT_CONFORMITE.md)
- **Architecture :** [ARCHITECTURE.md](ARCHITECTURE.md)
- **README :** [README.md](README.md)
- **TODO :** [TODO.md](TODO.md)

---

## 🎯 Scénario de démonstration complet

### **1. Démarrer l'application**
```powershell
.\mvnw.cmd spring-boot:run
```

### **2. Ouvrir Swagger UI**
http://localhost:8080/swagger-ui.html

### **3. Consulter les livres disponibles**
`GET /api/livres/disponibles`

### **4. Créer un nouveau membre**
`POST /api/membres`
```json
{
  "nom": "Test",
  "prenom": "Demo",
  "email": "demo@example.com",
  "typeMembre": "STANDARD",
  "quotaEmprunt": 3
}
```

### **5. Emprunter un livre**
`POST /api/emprunts?livreId=1&membreId=4`

### **6. Vérifier l'emprunt**
`GET /api/emprunts/membre/4`

### **7. Consulter les statistiques**
`GET /api/statistiques`

### **8. Retourner le livre**
`PUT /api/emprunts/1/retour`

### **9. Vérifier dans H2 Console**
http://localhost:8080/h2-console
```sql
SELECT * FROM emprunts;
SELECT * FROM livres;
```

---

**Dernière mise à jour :** 9 janvier 2026  
**Version :** 1.0.0  
**Spring Boot :** 2.7.18  
**Java :** 17+

