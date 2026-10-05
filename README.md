# Inventory Reservation API

A bounded Spring Boot service for products, stock, and temporary reservations. The interesting part is not CRUD: concurrent customers can request scarce stock, and the database-backed transaction must ensure stock never becomes negative.

## Architecture and stack

Java 21, Spring Boot 3.5, Spring Web, Spring Data JPA, Bean Validation, PostgreSQL, Flyway, Testcontainers, JUnit 5, Maven, Docker, and OpenAPI via springdoc. The service uses controller/DTO, service, repository, and domain layers. JPA entities never form the public API. See [Architecture](docs/ARCHITECTURE.md), [Database](docs/DATABASE.md), [API](docs/API.md), and [ADR 0001](docs/adr/0001-inventory-concurrency-strategy.md).

## Domain and lifecycle

One product has one inventory row. A reservation has one or more items, each referencing a product. New reservations are `PENDING`. Legal transitions are `PENDING -> CONFIRMED`, `PENDING -> CANCELLED`, and `PENDING -> EXPIRED`. Cancellation/expiration returns held stock; confirmation makes the deduction final. Terminal states reject further transitions.

## Transactions and concurrency

Reservation creation is a single `@Transactional` service operation. It loads products, locks all inventory rows with PostgreSQL `SELECT ... FOR UPDATE` semantics in deterministic UUID order, validates every requested quantity, decrements every row, and saves the reservation. Any failure rolls the transaction back. Pessimistic locking was chosen because it is direct and easy to explain for a single PostgreSQL service. It serializes contention on a product; it does not claim cross-database or distributed guarantees.

## Run

```bash
cp .env.example .env
# edit local credentials if desired
docker compose up --build
curl http://localhost:8080/actuator/health
```

OpenAPI UI is at `http://localhost:8080/swagger-ui.html`. Only `health` and `info` actuator endpoints are exposed. Credentials come from environment variables; `.env` is ignored and the example contains placeholders/local defaults only.

## Test

```bash
mvn test
docker compose config
docker build -t inventory-reservation-api:local .
```

Integration tests start clean PostgreSQL 17 containers, run Flyway, test repository/service/API behavior, transaction rollback, constraints, lifecycle transitions, and a mandatory race: with stock 5, two simultaneous requests for 4 yield exactly one success and final stock 1.

## API overview

- `POST /api/products`, `GET /api/products`, `GET /api/products/{id}`
- `PUT /api/inventory/{productId}`, `GET /api/inventory/{productId}`
- `POST /api/reservations`, `GET /api/reservations/{id}`
- `POST /api/reservations/{id}/confirm`, `POST /api/reservations/{id}/cancel`
- `GET /actuator/health`

Errors have timestamp, HTTP status/error, stable code, message, path, and validation details. See `docs/API.md` for requests and response shapes.

## Tradeoffs, security, limitations, future work

This is intentionally one service without authentication, messaging, distributed locks, or observability infrastructure. Use it behind appropriate network controls; change local credentials outside development and do not expose management endpoints broadly. Pessimistic locks favor correctness and simplicity but reduce throughput on hot products. Expiration is modeled but no scheduler runs it yet. Inventory adjustment is an administrative absolute-set operation; production systems would add authorization and an audit ledger. Future work could add idempotency keys, scheduled expiration, audit history, pagination, and authenticated administrative operations.

Created on 2026-10-04 as an AI-assisted portfolio project. Henrique completed the technical review and approved publication on 2026-10-05. The project makes no claim of historical or production use.
