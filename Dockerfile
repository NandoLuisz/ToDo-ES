# ====== ETAPA 1: BUILD ======
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copia apenas o pom primeiro (cache de dependências)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o restante do projeto
COPY src ./src

# Gera o JAR
RUN mvn clean package -DskipTests


# ====== ETAPA 2: RUNTIME ======
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/ToDo-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
