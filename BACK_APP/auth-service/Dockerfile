# --- Build stage ---
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Кэшируем зависимости отдельным слоем
COPY pom.xml .
COPY auth-service/pom.xml auth-service/pom.xml
COPY food-service/pom.xml food-service/pom.xml
RUN mvn -pl auth-service -am dependency:go-offline -B

# Копируем исходники и собираем
COPY . .
RUN mvn -pl auth-service -am clean package -DskipTests -B

# --- Run stage ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/auth-service/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]