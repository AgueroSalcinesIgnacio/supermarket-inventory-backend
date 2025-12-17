# Inventory Command Service

Microservice responsible for handling inventory write operations (commands) in the Supermarket Inventory System. It implements CQRS pattern (Command side) and Hexagonal Architecture.

## Features

- Create and update inventory items
- Manage stock levels (increase/decrease)
- Publish inventory events to Kafka (Event Sourcing)
- Secure endpoints using JWT authentication (RS256)
- PostgreSQL database for persistence
- Swagger/OpenAPI documentation

## Security

This service uses JWT (JSON Web Token) for authentication. It validates tokens signed by the Auth Service using RSA public key (RS256 algorithm).

### Authentication Flow
1. Clients obtain a JWT token from the Auth Service.
2. Clients include the token in the `Authorization` header: `Bearer <token>`.
3. This service validates the token signature using the configured Public Key.
4. If valid, the request is processed; otherwise, 401 Unauthorized is returned.

### Public Key Configuration
The Public Key is configured in `application.yaml`:

```yaml
jwt:
  public-key: "MIIBIjANBgkqhki..."
```

## API Documentation

The API documentation is available via Swagger UI when the service is running:

- Swagger UI: `http://localhost:8081/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8081/v3/api-docs`

Use the "Authorize" button in Swagger UI to authenticate with a Bearer token.

## Tech Stack

- Java 21
- Spring Boot 3.3.1
- Spring Security
- Spring Data JPA
- PostgreSQL
- Kafka (for event publishing)
- Lombok
- MapStruct

## Running the Service

1. Ensure the database (PostgreSQL) and Kafka are running (use `docker-compose`).
2. Run the application:
   ```sh
   ./mvnw spring-boot:run
   ```
