# Étape 1 : Build avec Maven et JDK 17
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copier d'abord le pom.xml pour télécharger les dépendances (mise en cache Docker)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copier le reste du code source
COPY src ./src
# Compiler le projet
RUN mvn clean package -DskipTests

# Étape 2 : Exécution avec JRE 17 (image plus légère)
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copier le .jar compilé depuis l'étape précédente
COPY --from=builder /app/target/monitoring-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port de Spring Boot
EXPOSE 8080

# Démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
