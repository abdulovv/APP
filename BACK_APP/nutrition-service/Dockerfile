FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY auth-service/pom.xml auth-service/pom.xml
COPY nutrition-service/pom.xml nutrition-service/pom.xml
RUN mvn -pl nutrition-service -am dependency:go-offline -B

COPY . .
RUN mvn -pl nutrition-service -am clean package -DskipTests -B

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/nutrition-service/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]