# Delivery Tracking Backend

A Spring Boot backend service for managing couriers and tracking courier location history in real time.

## Overview

This project provides:

- Basic user registration and login endpoints
- Courier management (create, read, update, list)
- Courier location tracking and history
- Latest location caching with Redis
- PostgreSQL persistence with Flyway migrations
- Standardized API response wrappers and global exception handling

## Tech Stack

- Java 25 (configured in `pom.xml`)
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- Spring Security
- Spring Validation
- Spring Data Redis
- PostgreSQL
- Flyway
- Maven Wrapper (`mvnw`)

## Project Structure

```text
src/main/java/com/lrn/delivery_tracking
├── cache/                # Redis cache services
├── config/               # Security and configuration classes
├── controller/           # REST controllers
├── dto/
│   ├── request/          # Request DTOs with validation
│   └── response/         # Response DTOs and wrappers
├── entity/               # JPA entities
├── enums/                # Domain enums (Role, CourierStatus)
├── exception/            # Custom exceptions and global handler
├── mapper/               # Entity <-> DTO mappers
├── repository/           # Spring Data repositories
├── service/
│   ├── impl/             # Service implementations
│   └── *.java            # Service contracts
└── DeliveryTrackingApplication.java

src/main/resources
├── application.properties
├── application-example.properties
└── db/migration/         # Flyway SQL migrations
```

## Data Model

### `couriers`
- `id`, `name`, `surname`, `email` (unique), `phone` (unique)
- `status` (`AVAILABLE`, `BUSY`, `OFFLINE`)
- `created_at`, `updated_at`

### `courier_locations`
- `id`, `courier_id` (FK to `couriers`)
- `latitude`, `longitude`, optional `speed`, `heading`, `accuracy`
- `recorded_at`, `created_at`
- Index on `(courier_id, recorded_at)`

### `users`
- `id`, `first_name`, `last_name`, `email` (unique)
- `password_hash`, `role` (`ADMIN`, `CUSTOMER`, `COURIER`)
- `enabled`, `created_at`, `updated_at`

## Configuration

Use `src/main/resources/application-example.properties` as a template:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/<YOUR_DATABASE_NAME>
spring.datasource.username=<YOUR_USER_NAME>
spring.datasource.password=<YOUR_PASSWORD>

spring.data.redis.host=localhost
spring.data.redis.port=6379
```

Important behavior:

- Flyway is enabled and runs migrations on startup
- JPA `ddl-auto` is `validate`
- Redis is used to cache the latest location for each courier

## Prerequisites

Install and run:

- Java (version matching project configuration: Java 25)
- PostgreSQL
- Redis

## Running the Application

1. Create a PostgreSQL database.
2. Configure `application.properties` with database and Redis values.
3. Start PostgreSQL and Redis.
4. Run:

```bash
sh mvnw spring-boot:run
```

Or build a jar:

```bash
sh mvnw clean package
java -jar target/delivery-tracking-0.0.1-SNAPSHOT.jar
```

## API

Base path: `/api`

### Authentication

#### `POST /api/auth/register`
Registers a user.

Request body:

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "password": "secret"
}
```

#### `POST /api/auth/login`
Authenticates with email and password.

Request body:

```json
{
  "email": "john@example.com",
  "password": "secret"
}
```

### Courier Management

#### `POST /api/couriers`
Creates a courier.

#### `GET /api/couriers/{id}`
Returns courier details by ID.

#### `GET /api/couriers?page=0&size=50&sortBy=desc`
Returns paginated couriers sorted by `createdAt`.

#### `PUT /api/couriers/{id}`
Updates courier phone.

### Courier Location Tracking

#### `POST /api/couriers/{id}/locations`
Adds a location point for a courier.

Request body:

```json
{
  "latitude": 41.0082,
  "longitude": 28.9784,
  "speed": 35.10,
  "heading": 135.00,
  "accuracy": 5.00,
  "recordedAt": "2026-05-13T10:20:30Z"
}
```

#### `GET /api/couriers/{courierId}/locations?page=0&size=30`
Returns paginated location history (newest first).

#### `GET /api/couriers/{courierId}/locations/latest`
Returns latest courier location.
Checks Redis cache first, falls back to database if cache miss.

## Response Format

Successful endpoints return:

```json
{
  "data": {},
  "status": 200,
  "message": "..."
}
```

Paginated endpoints return `PageResponse` in `data`:

```json
{
  "data": {
    "data": [],
    "page": 0,
    "size": 30,
    "totalElements": 100,
    "totalPages": 4
  },
  "status": 200,
  "message": "..."
}
```

## Validation and Errors

Validation is applied using Jakarta Bean Validation on request DTOs.

Custom exception handling includes:

- `AlreadyExistsException` -> `409 CONFLICT`
- `NotFoundException` -> `404 NOT_FOUND`
- `InvalidCredentialsException` -> `400 BAD_REQUEST`
- `UserDisabledException` -> `400 BAD_REQUEST`
- Validation errors -> `400 BAD_REQUEST` with field-level details

## Security

`/api/auth/**` endpoints are publicly accessible.

Current security configuration permits all other endpoints as well.
Password hashing uses `BCryptPasswordEncoder`.

## Testing

Run tests with:

```bash
sh mvnw test
```

Current test suite includes a Spring context load test (`DeliveryTrackingApplicationTests`).

## Notes

- Latest location cache key format: `courier:{courierId}:location`
- Cache TTL for latest location is `10 minutes`
- Migrations are located under `src/main/resources/db/migration`

