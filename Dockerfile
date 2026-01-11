# ==========================================
# ÉTAPE 1 : BUILD (Maven compile le projet)
# ==========================================
FROM maven:3.8.6-eclipse-temurin-17 AS build

LABEL maintainer="bibliotheque@esiea.fr"
LABEL description="Application de Gestion de Bibliothèque - Backend Spring Boot"
LABEL version="1.0"

WORKDIR /app

# Copier pom.xml et télécharger les dépendances (cache Docker)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copier le code source
COPY src ./src

# Compiler le projet (skip tests pour accélérer)
RUN mvn clean package -DskipTests

# ==========================================
# ÉTAPE 2 : RUNTIME (Image finale légère)
# ==========================================
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copier le JAR depuis l'étape de build
COPY --from=build /app/target/gestion-bibliotheque-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port
EXPOSE 8080

# Variables d'environnement par défaut (overridées par Render)
ENV SPRING_PROFILES_ACTIVE=prod
ENV KAFKA_ENABLED=false

# Point d'entrée optimisé pour le cloud
ENTRYPOINT ["java", \
            "-XX:+UseContainerSupport", \
            "-XX:MaxRAMPercentage=75.0", \
            "-Djava.security.egd=file:/dev/./urandom", \
            "-jar", \
            "app.jar"]

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1