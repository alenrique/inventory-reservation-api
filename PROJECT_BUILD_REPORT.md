# Project Build Report

- Objective: demonstrate a real transactional inventory-reservation backend without overstating production use.
- Approved stack: Java 21, Spring Boot 3.5, PostgreSQL, Docker, Maven, Flyway, Testcontainers/JUnit.
- Creation date: 2026-10-04; no backdating or historical association.
- Architecture: single layered service with DTO boundary and PostgreSQL persistence.
- Domain: Product, Inventory, Reservation, ReservationItem; explicit pending/confirmed/cancelled/expired state machine.
- API: product, inventory, reservation creation/read, confirm/cancel, health, OpenAPI.
- Transactions/concurrency: use-case transaction plus deterministic pessimistic row locking; rollback and concurrent oversell tests.
- Database: explicit constraints, FKs, indexes, and Flyway V1 migration; Hibernate validates only.
- Docker: multi-stage non-root image and Compose application/PostgreSQL stack.
- Documentation: README, architecture, database, API, and concurrency ADR.
- Validation: Testcontainers 1.21.4 connected to Docker 29.8.0 through the local Unix socket and started real PostgreSQL 17.11 containers. Two consecutive full `mvn test` runs passed 10/10 tests with zero failures, errors, or skips. Each integration context began with an empty schema, Flyway applied V1, and Hibernate validated the resulting schema. The concurrency test used inventory 5 and two simultaneous requests for 4: exactly one succeeded, one was rejected, and final inventory was 1. `mvn clean package` and `docker compose config` passed. Earlier Compose smoke validation also passed.
- Known limitations: no authentication, audit ledger, automatic expiry scheduler, distributed guarantees, or production history.
- Future improvements: idempotency keys, expiry worker, audit events, pagination, authorized administration.
- AI-assisted status: AI-assisted construction; candidate review and ability to explain are required.
- Publication: not published; waiting for human review. Evidence: not imported.
- Git commits: `bd9d02b` initialization; `1f32ca8` transactional domain/API; `1f602ab` PostgreSQL and concurrency tests; `878c7b6` documentation; `2c2504f` Testcontainers/Docker validation fix.
- Final state: `WAITING_HUMAN_REVIEW`; publication remains `NOT_AUTHORIZED`; evidence remains `NOT_IMPORTED`.
