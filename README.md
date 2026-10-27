# Stelloom

Stelloom is a full-stack collaboration platform built with Spring Boot. It supports direct and group chat over WebSockets, an org-wide feed with posts, comments, and likes, and media sharing through AWS S3. Security is handled with Spring Security and JWT, and performance is improved with Redis caching, query optimization, and connection pooling.

**Status:** 🚧 In development (early stage)

---

## About

Stelloom is a full-stack collaboration platform built with Spring Boot. It combines real-time messaging with an organization-wide feed, so teams can chat, share updates, and exchange media in one secure workspace.

## Features

Legend: ✅ done · 🚧 in progress · ⬜ planned

### Core
- ⬜ User registration and login (JWT)
- ⬜ Direct (one-to-one) chat
- ⬜ Group chat
- ⬜ Message history with pagination
- ⬜ Org-wide feed with posts
- ⬜ Comments and likes
- ⬜ Media upload and sharing (AWS S3, presigned URLs)
- ⬜ Role-based access (Employee, Admin)

### Performance
- ⬜ Redis caching (feed, profiles, room lists)
- ⬜ Query optimization and indexing
- ⬜ Connection pooling (HikariCP) tuning
- ⬜ Cursor-based pagination

### Optional / Stretch
- ⬜ Pinned announcements
- ⬜ Polls
- ⬜ Employee directory
- ⬜ Typing indicators and online status
- ⬜ Notifications
- ⬜ Read receipts

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot (Web, Security, Data JPA, WebSocket, Validation) |
| Auth | Spring Security + JWT |
| Real-time | WebSocket (STOMP) |
| Database | PostgreSQL 16 |
| Migrations | Flyway |
| Cache | Redis 7 |
| Storage | AWS S3 |
| Build | Maven |
| Containers | Docker, Docker Compose |
| Tooling | Lombok, MapStruct |

## Architecture

_To be added: high-level diagram, data flow for chat delivery, caching strategy._

Planned components:
- REST API for auth, feed, rooms, and media
- WebSocket (STOMP) endpoint for real-time messages
- PostgreSQL as the primary store, with schema managed by Flyway
- Redis for caching (and later, multi-instance message fan-out)
- S3 for media, accessed through presigned URLs

## Getting Started

### Prerequisites

- Java 21
- Docker Desktop (running)
- Maven (or use the included `./mvnw`)

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/Sanskar00/stelloom.git
   cd stelloom
   ```

2. **Start PostgreSQL and Redis**
   ```bash
   docker compose up -d
   ```
   (Or let Spring Boot start them automatically when you run the app.)

3. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

4. Flyway runs automatically on startup and creates the schema.

### Local defaults

| Service | Host | Port | Database | User |
|---|---|---|---|---|
| PostgreSQL | localhost | 5432 | `stelloom` | `stelloom` |
| Redis | localhost | 6379 | n/a | n/a |

Local passwords are development-only defaults. Never reuse them in other environments.

## Configuration

Configuration lives in `src/main/resources/application.yml`. Values can be overridden with environment variables:

| Variable | Purpose |
|---|---|
| `SPRING_DATASOURCE_URL` | JDBC URL |
| `SPRING_DATASOURCE_USERNAME` | Database user |
| `SPRING_DATASOURCE_PASSWORD` | Database password |
| `SPRING_DATA_REDIS_HOST` | Redis host |
| `SPRING_DATA_REDIS_PASSWORD` | Redis password |
| `JWT_SECRET` | JWT signing secret (required in prod) |
| `AWS_REGION` | AWS region |
| `AWS_S3_BUCKET` | S3 bucket name |
| `AWS_S3_ENDPOINT` | Custom endpoint (LocalStack only) |
| `SPRING_PROFILES_ACTIVE` | `test` or `prod` |

Never commit real secrets. Use `.env.example` as a template and keep real `.env` files out of Git.

## Project Structure

```
stelloom/
├── src/main/java/com/sanskar/project/stelloom/
│   ├── StelloomApplication.java
│   ├── auth/                # JWT filter, auth controller & services
│   ├── chat/                # Chat feature
│   ├── common/
│   │   └── exception/       # Global exception handling
│   ├── config/              # Security & app configuration
│   ├── feed/                # Feed feature
│   ├── media/               # Media handling
│   └── user/                # User entity, repository, services
├── src/main/resources/
│   ├── application.yml
│   └── db/migration/        # Flyway migrations (V1__init.sql, ...)
├── compose.yaml
├── pom.xml
└── README.md
```

## Database

Schema changes are managed only through Flyway migrations in `src/main/resources/db/migration`.

Core tables: `users`, `chat_rooms`, `chat_room_members`, `messages`, `posts`, `comments`, `likes`.

Rules:
- Never edit a migration that has already been applied. Add a new `V<n>__description.sql` instead.
- Hibernate runs with `ddl-auto: validate`, so it only checks the schema and never changes it.

## API Documentation

_To be added: Swagger UI (springdoc-openapi) at `/swagger-ui.html`._

## Performance Benchmarks

_To be added: before/after results for Redis caching, query optimization, and connection pooling (tool used, p95 latency, throughput)._

| Endpoint | Before | After | Improvement |
|---|---|---|---|
| _TBD_ | | | |

## Roadmap

1. Foundation: project setup, Docker Compose, Flyway schema, entities
2. Auth and security: register, login, JWT, security filter chain
3. Feed: posts, comments, likes, paginated feed
4. Real-time chat: WebSocket with JWT-secured handshake, rooms, history
5. Media: S3 uploads with presigned URLs
6. Performance: Redis caching, indexing, pool tuning, benchmarks
7. Polish: tests, API docs, Dockerfile, CI, deployment

## Testing

_To be added: unit tests (JUnit 5, Mockito) and integration tests (Testcontainers)._

## Deployment

_To be added: test and production environment setup (Docker Compose or AWS with RDS and ElastiCache)._

## Contributing

This is a personal learning project. Suggestions are welcome through issues.

## License

_To be decided._

## Author

**Sanskar Saxena**
