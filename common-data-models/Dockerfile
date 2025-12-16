FROM maven:3.9-eclipse-temurin-21
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests
CMD ["echo", "Common Data Models library built successfully. This container does not run a service."]
