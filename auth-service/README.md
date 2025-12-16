# Auth Service

Microservice responsible for authentication and authorization within the Supermarket Inventory Backend system. It handles user registration, login, and JWT generation using Spring Security.

## Technologies Used

*   **Java 21**: Core programming language.
*   **Spring Boot 3.3.1**: Application framework.
*   **Spring Security**: Authentication and access control.
*   **JWT (JSON Web Token)**: Stateless authentication mechanism.
*   **PostgreSQL**: Relational database for user storage.
*   **Spring Data JPA**: Data persistence.
*   **Lombok**: Boilerplate code reduction.
*   **MapStruct**: Object-to-object mapping.
*   **SpringDoc OpenAPI**: API documentation (Swagger).
*   **Spotless**: Code formatting.
*   **Jacoco**: Code coverage.

## Prerequisites

*   Java Development Kit (JDK) 21
*   Maven (or use the provided `mvnw` wrapper)
*   PostgreSQL Database (running locally or via Docker)
    *   Creates 3 databases: `auth`, `inventory_write`, `inventory_read`
    *   Auth Schema Tables: `USERS`, `ROLES`, `USERS_ROLES`

## Configuration

The service uses standard Spring Boot configuration files located in `src/main/resources`.

### Profiles

*   **default**: Basic application settings.
*   **local**: Settings for local development (database connection).

### Environment Variables / Properties

Key properties configured in `application-local.yaml`:

```yaml
spring:
  datasource:
    url: "jdbc:postgresql://localhost:5432/auth"
    username: "postgres"
    password: "password"
  jpa:
    hibernate:
      ddl-auto: "update"
```

## Installation and Running

### Build

To build the project and run tests:

```bash
./mvnw clean install
```

### Run Locally

To run the application with the `local` profile (ensure your Postgres DB is running):

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

The server will start on port `8080` (default) or as configured.

## API Endpoints

Base URL: `/api/auth`

### 1. Register User

*   **URL**: `/register`
*   **Method**: `POST`
*   **Description**: Registers a new user in the system.
*   **Request Body** (`application/json`):

    ```json
    {
      "username": "jdoe",
      "email": "jdoe@example.com",
      "password": "securePassword123"
    }
    ```

    *   `username` (required): 3-50 chars
    *   `email` (required): Valid email format
    *   `password` (required): 6-100 chars

*   **Response** (`201 Created`):

    ```json
    {
      "token": "eyJhbGciOiJIUzI1NiJ9...",
      "username": "jdoe",
      "email": "jdoe@example.com"
    }
    ```

### 2. Login

*   **URL**: `/login`
*   **Method**: `POST`
*   **Description**: Authenticates a user and returns a JWT token.
*   **Request Body** (`application/json`):

    ```json
    {
      "username": "jdoe",
      "password": "securePassword123"
    }
    ```

*   **Response** (`200 OK`):

    ```json
    {
      "token": "eyJhbGciOiJIUzI1NiJ9...",
      "username": "jdoe",
      "email": "jdoe@example.com"
    }
    ```

## Development

### Code Formatting

This project uses **Spotless** to ensure code consistency. To verify and apply formatting:

```bash
./mvnw spotless:check
./mvnw spotless:apply
```

### API Documentation

Once the application is running, you can access the Swagger UI documentation at:

```
http://localhost:8080/swagger-ui.html
```
