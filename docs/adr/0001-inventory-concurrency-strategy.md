# ADR 0001: PostgreSQL pessimistic inventory locking

Date: 2026-10-04

## Context

Two transactions can both observe enough stock and oversell if they read and update without coordination.

## Decision

Lock requested inventory rows using JPA `PESSIMISTIC_WRITE`, backed by PostgreSQL row locks, inside the reservation transaction. Acquire multi-product locks in UUID order. Validate and mutate only after all rows are locked.

## Alternatives

Optimistic locking with a version column and bounded retries can improve uncontended throughput but adds conflict/retry behavior. A single atomic SQL decrement is excellent for one item but complicates an all-or-nothing multi-item reservation. Distributed locks add operational complexity and would not replace database constraints.

## Consequences

Concurrent reservation attempts for the same product serialize, preventing lost updates and overselling. Hot products can experience waiting and lower throughput. Guarantees apply to this service and PostgreSQL transaction boundary, not multiple databases or arbitrary external writers.
