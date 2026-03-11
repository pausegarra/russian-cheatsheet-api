# Russian Cheatsheet

Backend service for managing a Russian vocabulary catalogue, exposing CRUD-style word endpoints, publication workflows, and a scheduled Memrise import.

Built with Quarkus, Java 21, Maven, PostgreSQL, Flyway, and Keycloak-based JWT authentication.

## What It Does

- Stores Russian words with translations and grammatical metadata.
- Supports declinations, conjugations, and declination matrices depending on word type.
- Exposes public read endpoints for listing and fetching words.
- Exposes protected write endpoints for creating, updating, publishing, and reviewing unpublished words.
- Syncs new words from Memrise every Monday at `01:00`.

## Stack

- Java 21
- Quarkus 3
- Maven Wrapper
- PostgreSQL 15
- Flyway
- Keycloak / JWT
- OpenAPI / Swagger UI

## Requirements

- JDK 21
- Docker and Docker Compose

## Configuration

Main configuration lives in [application.yaml](src/main/resources/application.yaml) and the local development overrides are in [application-dev.yaml](src/main/resources/application-dev.yaml).

Important settings:

- API root path: `/api`
- Default HTTP port: `8080`
- PostgreSQL URL: `jdbc:postgresql://localhost:5432/russian-cheatsheet`
- Swagger UI: `http://localhost:8080/api/q/swagger-ui`
- OpenAPI document: `http://localhost:8080/api/q/openapi`

Environment variables used in development:

- `MEMRISE_TOKEN`: bearer token used by the scheduled Memrise sync

## Local Setup

1. Start PostgreSQL:

```sh
docker compose up -d
```

2. Export the Memrise token if you want the sync job to work in dev mode:

```sh
export MEMRISE_TOKEN=your-token
```

3. Run the app:

```sh
./mvnw clean quarkus:dev --debug -DskipTests
```

The API will be available at `http://localhost:8080/api`.

## Common Commands

```sh
make help          # list available tasks
make dev           # run in Quarkus dev mode
make run           # package and run
make test          # run tests with the test profile
make build         # create a JVM build
make build-native  # create a native build
make start-db      # start PostgreSQL
make stop-db       # stop PostgreSQL
make remove-db     # stop PostgreSQL and delete its volume
```

## API Overview

Public endpoints:

- `GET /api/words`
- `GET /api/words/{id}`

Protected endpoints:

- `POST /api/words`
- `PUT /api/words/{wordId}`
- `PATCH /api/words/{wordId}/publish`
- `GET /api/words/unpublished`
- `GET /api/auth/profile/permissions`

Role requirements on protected word endpoints:

- `words#create`
- `words#update`
- `words#publish`

Use Swagger UI during local development for the current request and response schemas.

## Database

Flyway migrations run automatically on startup. Migration files live in [src/main/resources/db/migration](src/main/resources/db/migration).

To generate a new migration file:

```sh
make new-migration
```

## Testing

Run the test suite with:

```sh
./mvnw clean test -Dquarkus.profile=test
```

The project includes unit, integration, and architectural tests.

## Packaging

JVM build:

```sh
./mvnw clean package -DskipTests
```

Native build:

```sh
./mvnw clean package -Pnative -DskipTests
```

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE).
