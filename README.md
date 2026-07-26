# SmartNotify — Backend

Real-time notification system backend built with Spring Boot 4, Spring Security, JWT authentication, and WebSocket (STOMP).

## Tech Stack

- Java 21
- Spring Boot 4
- Spring Security (JWT, BCrypt)
- Spring Data JPA / Hibernate
- MySQL
- Spring WebSocket (STOMP)
- Springdoc OpenAPI (Swagger)
- Maven

## Features

- JWT-based authentication with role-based authorization (Admin / User)
- Real-time notification delivery via WebSocket (targeted + broadcast)
- IDOR-protected notification access (users can only access their own data)
- Paginated notification listing with read/unread tracking
- Centralized exception handling with consistent API response format
- Fully documented REST API via Swagger UI

## Getting Started

### Prerequisites
- JDK 21
- Maven 3.9+
- MySQL 8+

### Setup

1. Clone the repository:
```bash
   git clone https://github.com/Diveshpatelsinha/smartnotify.git
   cd smartnotify
```

2. Create a MySQL database (or let the app auto-create it):
```sql
   CREATE DATABASE smartnotify_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

3. Configure `src/main/resources/application.properties`:
```properties
   spring.datasource.username=your_mysql_username
   spring.datasource.password=your_mysql_password
   smartnotify.jwt.secret=<generate with: openssl rand -base64 32>
```

4. Run the application:
```bash
   ./mvnw spring-boot:run
```

5. API documentation available at:

6. http://localhost:8080/swagger-ui.html


## API Overview

| Method | Endpoint | Access |
|---|---|---|
| POST | `/api/auth/register` | Public |
| POST | `/api/auth/login` | Public |
| GET | `/api/users/me` | Authenticated |
| GET | `/api/notifications` | Authenticated |
| GET | `/api/notifications/{id}` | Authenticated |
| PUT | `/api/notifications/{id}/read` | Authenticated |
| DELETE | `/api/notifications/{id}` | Authenticated |
| GET | `/api/notifications/dashboard/stats` | Authenticated |
| POST | `/api/admin/notifications/send` | Admin only |
| POST | `/api/admin/notifications/broadcast` | Admin only |
| GET | `/api/admin/notifications` | Admin only |
| DELETE | `/api/admin/notifications/{id}` | Admin only |

## Architecture

Feature-based layered architecture (Controller → Service → Repository) with DTO pattern, manual entity/DTO mapping, and centralized `@RestControllerAdvice` exception handling.

src/main/java/com/smartnotify/
├── config/ # Security beans, CORS, WebSocket, OpenAPI config
├── security/ # JWT filter/util, UserDetailsService, handshake auth
├── exception/ # Custom exceptions + global exception handler
├── common/ # Shared ApiResponse<T> wrapper
└── feature/
├── auth/
├── user/
└── notification/


## ⚠️ Security Note

This is a learning/portfolio project. `application.properties` contains configuration values directly for simplicity. In a production deployment, secrets (`spring.datasource.password`, `smartnotify.jwt.secret`) should be externalized via environment variables and never committed to source control.

## Related Repository

Frontend (Angular 21): [smartnotify-frontend](https://github.com/Diveshpatelsinha/smartnotify-frontend)
