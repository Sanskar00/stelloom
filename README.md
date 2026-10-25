# stelloom
Stelloom is a team collaboration platform that combines real-time chat with an organization-wide feed. Members can message each other directly or in groups, share posts and announcements, comment and like, and attach media, all in one secure workspace.

About

Stelloom is a full-stack collaboration platform built with Spring Boot. It combines real-time messaging with an organization-wide feed, so teams can chat, share updates, and exchange media in one secure workspace.

Features

Legend: ✅ done · 🚧 in progress · ⬜ planned

Core
⬜ User registration and login (JWT)
⬜ Direct (one-to-one) chat
⬜ Group chat
⬜ Message history with pagination
⬜ Org-wide feed with posts
⬜ Comments and likes
⬜ Media upload and sharing (AWS S3, presigned URLs)
⬜ Role-based access (Employee, Admin)
Performance
⬜ Redis caching (feed, profiles, room lists)
⬜ Query optimization and indexing
⬜ Connection pooling (HikariCP) tuning
⬜ Cursor-based pagination
Optional / Stretch
⬜ Pinned announcements
⬜ Polls
⬜ Employee directory
⬜ Typing indicators and online status
⬜ Notifications
⬜ Read receipts
Tech Stack
Layer	Technology
Language	Java 21
Framework	Spring Boot (Web, Security, Data JPA, WebSocket, Validation)
Auth	Spring Security + JWT
Real-time	WebSocket (STOMP)
Database	PostgreSQL 16
Migrations	Flyway
Cache	Redis 7
Storage	AWS S3
Build	Maven
Containers	Docker, Docker Compose
Tooling	Lombok, MapStruct
Architecture

To be added: high-level diagram, data flow for chat delivery, caching strategy.

Planned components:

REST API for auth, feed, rooms, and media
WebSocket (STOMP) endpoint for real-time messages
PostgreSQL as the primary store, with schema managed by Flyway
Redis for caching (and later, multi-instance message fan-out)
S3 for media, accessed through presigned URLs
Getting Started
Prerequisites
Java 21
Docker Desktop (running)
Maven (or use the included ./mvnw)
Setup
Clone the repository
bash
   git clone https://github.com/<your-username>/stelloom.git
   cd stelloom
Start PostgreSQL and Redis
bash
   docker compose up -d

(Or let Spring Boot start them automatically when you run the app.)

Run the application
bash
   ./mvnw spring-boot:run
Flyway runs automatically on startup and creates the schema.
Local defaults
Service	Host	Port	Database	User
PostgreSQL	localhost	5432	stelloom	stelloom
Redis	localhost	6379	n/a	n/a

Local passwords are development-only defaults. Never reuse them in other environments.

Configuration

Configuration lives in src/main/resources/application.yml. Values can be overridden with environment variables:

Variable	Purpose
SPRING_DATASOURCE_URL	JDBC URL
SPRING_DATASOURCE_USERNAME	Database user
SPRING_DATASOURCE_PASSWORD	Database password
SPRING_DATA_REDIS_HOST	Redis host
SPRING_DATA_REDIS_PASSWORD	Redis password
JWT_SECRET	JWT signing secret (required in prod)
AWS_REGION	AWS region
AWS_S3_BUCKET	S3 bucket name
AWS_S3_ENDPOINT	Custom endpoint (LocalStack only)
SPRING_PROFILES_ACTIVE	test or prod

Never commit real secrets. Use .env.example as a template and keep real .env files out of Git.

Project Structure
stelloom/
├── src/main/java/com/sanskar/stelloom/
│   ├── config/
│   ├── controller/
│   ├── dto/
│   ├── exception/
│   ├── mapper/
│   ├── model/
│   ├── repository/
│   ├── security/
│   └── service/
├── src/main/resources/
│   ├── application.yml
│   └── db/migration/        # Flyway migrations (V1__init.sql, ...)
├── compose.yaml
├── pom.xml
└── README.md
Database

Schema changes are managed only through Flyway migrations in src/main/resources/db/migration.

Core tables: users, chat_rooms, chat_room_members, messages, posts, comments, likes.

Rules:

Never edit a migration that has already been applied. Add a new V<n>__description.sql instead.
Hibernate runs with ddl-auto: validate, so it only checks the schema and never changes it.
API Documentation

To be added: Swagger UI (springdoc-openapi) at /swagger-ui.html.

Performance Benchmarks

To be added: before/after results for Redis caching, query optimization, and connection pooling (tool used, p95 latency, throughput).

Endpoint	Before	After	Improvement
TBD			
Roadmap
Foundation: project setup, Docker Compose, Flyway schema, entities
Auth and security: register, login, JWT, security filter chain
Feed: posts, comments, likes, paginated feed
Real-time chat: WebSocket with JWT-secured handshake, rooms, history
Media: S3 uploads with presigned URLs
Performance: Redis caching, indexing, pool tuning, benchmarks
Polish: tests, API docs, Dockerfile, CI, deployment
Testing

To be added: unit tests (JUnit 5, Mockito) and integration tests (Testcontainers).

Deployment

To be added: test and production environment setup (Docker Compose or AWS with RDS and ElastiCache).

Contributing

This is a personal learning project. Suggestions are welcome through issues.

License

To be decided.

Author

Sanskar Saxena
