FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy dependencies from the common-data-models image (built by docker-compose)
COPY --from=common-data-models:latest /root/.m2/repository/com/supermarket /root/.m2/repository/com/supermarket

# Build inventory-consumer-service
COPY inventory-consumer-service/pom.xml inventory-consumer-service/
COPY inventory-consumer-service/src inventory-consumer-service/src
RUN mvn -f inventory-consumer-service/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/inventory-consumer-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
