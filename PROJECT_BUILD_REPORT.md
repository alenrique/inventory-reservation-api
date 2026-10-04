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
- Validation: Maven package passed on Java 21.0.12.1. Two domain unit tests passed. Eight PostgreSQL/Testcontainers tests are implemented (including rollback and concurrency), but the full Maven run could not start Testcontainers because the Maven process lacked direct Docker-socket access. `docker compose config` passed. A Docker image built successfully (with host networking required for Maven dependency DNS). Compose started from an empty PostgreSQL 17 volume, Flyway applied V1, health returned 200, and create product/set stock/reserve/cancel smoke operations passed. Containers were stopped after validation.
- Known limitations: no authentication, audit ledger, automatic expiry scheduler, distributed guarantees, or production history.
- Future improvements: idempotency keys, expiry worker, audit events, pagination, authorized administration.
- AI-assisted status: AI-assisted construction; candidate review and ability to explain are required.
- Publication: not published; waiting for human review. Evidence: not imported.
- Git commits: to be filled from the real local repository after validation.
