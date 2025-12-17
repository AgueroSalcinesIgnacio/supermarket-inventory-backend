FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy dependencies from the common-data-models image (built by docker-compose)
COPY --from=common-data-models:latest /root/.m2/repository/com/supermarket /root/.m2/repository/com/supermarket

# Build inventory-command-service
COPY inventory-command-service/pom.xml inventory-command-service/
COPY inventory-command-service/src inventory-command-service/src
RUN mvn -f inventory-command-service/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/inventory-command-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
