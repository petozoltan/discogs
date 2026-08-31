# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

A Spring Boot 4.0 / Java 25 REST API (learning project) for managing musicians, groups, and recordings, with full HATEOAS hypermedia support and Spring Data JPA.

See `AGENTS.md` for a detailed architecture reference and feature-development checklist.

## Commands

```bash
# Build
./mvnw clean package

# Run (no data seeding)
./mvnw spring-boot:run

# Run with H2 data seeding
./mvnw spring-boot:run -Dspring-boot.run.arguments=preloaddata

# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=PersonRepositoryTest
```

App runs on **port 8085**. H2 console at `http://localhost:8085/h2` (JDBC URL `jdbc:h2:mem:mydb`, user `sa`, password `pwd`).

Docker: build the fat JAR first (`mvn clean package`), then `docker build -t discogs:tag .` and pass `preloaddata` as a CMD argument to seed the DB.

## Architecture

**No service layer** — controllers call repositories directly.

Three domain packages (`person`, `group`, `recording`), each with the same four-file structure:
- `{Entity}.java` — `@Entity` with `@NaturalId` on the `name` field and `equals`/`hashCode` based on it
- `{Entity}Repository.java` — extends `JpaRepository<T, Long>` with derived query methods
- `{Entity}Controller.java` — `@RestController` following GET/POST/GET-by-id/PUT/PATCH/DELETE + nested sub-resource endpoints
- `{Entity}ModelAssembler.java` — `RepresentationModelAssembler` adding HATEOAS links via `linkTo(methodOn(...))`

Cross-cutting utilities in `data/common/`:
- `RestHelper` — `toResponseCreated()` and `copyAttribute(src, dst, copyNulls)` used for PUT (`copyNulls=true`) vs PATCH (`copyNulls=false`)
- `EntityControllerAdvice` — `@RestControllerAdvice` maps `EntityNotFoundException` → 404

Enums in `data/values/`: `Country`, `Gender`, `Genre`, `Instrument`, `RecordingType`.

## JPA Relations

- `Group` ↔ `Person`: `@ManyToMany`, join table `GROUP_MEMBER`. `Group` owns the relation; `Person.groups` is `mappedBy="members"`.
- `Group` → `Recording`: `@OneToMany(cascade=ALL, orphanRemoval=true)`. Saving a `Group` cascades to its `Recording`s.
- Both sides use `FetchType.EAGER`.
- `Group` maps to table `BAND` (SQL reserved-word workaround).

## Data Seeding

`MockData.java` (`ApplicationRunner`) seeds 13 Persons, 3 Groups, 9 Recordings only when the JVM arg `preloaddata` is present. The H2 schema is dropped and recreated on every startup (`ddl-auto: create`). The `data.sql` file exists but is disabled (`spring.sql.init.mode: never`).

`PersonRepositoryTest` saves its own test data per test and rolls back via `@Transactional`; it does not depend on `MockData`.

## Known Incomplete Areas

- Cross-entity sub-resource endpoints (`/persons/{id}/groups`, `/persons/{id}/recordings`) return hardcoded mock data — real JPA queries are a TODO.
- Conditional HATEOAS links in `ModelAssembler`s are not yet implemented.
