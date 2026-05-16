# AGENTS.md

## Stack

- Java 21, Quarkus 3.19.2, Maven (wrapper: `./mvnw`)
- PostgreSQL 15 (local), Flyway migrations, Hibernate ORM Panache
- Keycloak JWT auth (SmallRye JWT), Lombok
- ArchUnit enforces architecture rules in tests

## Commands

```sh
make dev           # quarkus:dev (skips tests, debug port 5005)
make test          # ./mvnw clean test -Dquarkus.profile=test
make build         # ./mvnw clean package -DskipTests
make build-native  # native image build
make start-db      # docker compose up -d (postgres)
make stop-db       # docker compose down
make new-migration # interactive, creates V<timestamp>__<name>.sql
make generate-schema # dumps schema.sql from Hibernate
```

No lint/typecheck step. `make test` is the verification command.

## Architecture

Clean Architecture, base package: `es.pausegarra.russian_cheatsheet`

```
common/                  # shared domain, application, infrastructure
  domain/                # value objects, pagination, exceptions
  application/           # UseCase<T,R> interface, locale, DTOs
  infrastructure/        # exception mappers, audit, filters
context/
  words/                 # bounded context: Russian word CRUD
    domain/              # entities, repository interfaces, enums (WordType)
    application/         # use cases (create/update/publish/find), DTOs
    infrastructure/      # REST resources, Panache repos, models, Memrise cron/client
  auth/                  # bounded context: Keycloak role lookup
    domain/              # KeycloakRepository interface
    application/         # FindAuthRolesService
    infrastructure/      # REST resource, KeycloakConfig
```

Layer rules (enforced by ArchUnit):
- Domain never depends on application/infrastructure/rest
- Application never depends on infrastructure/rest
- Domain repository interfaces are in `domain/repositories/`
- Repository implementations must be in `infrastructure/`
- UseCase implementations annotated `@ApplicationScoped` live in `application/`

## Naming

- UseCase implementations: suffix `Service` (e.g., `CreateWordUseCase` → impl named `*Service`)
- REST resources: suffix `Resource`
- JPA entities (Panache): suffix `Model` in `infrastructure/models/`
- Domain entities: suffix `Entity` in `domain/entities/`
- Test data builders: suffix `Mother` in test `mother/` package

## Testing

```sh
make test   # runs: ./mvnw clean test -Dquarkus.profile=test
```

- **Integration tests** (`*IT.java`): extend `IntegrationTest` base class, use `@QuarkusTest`, `@TestSecurity` for auth mocking, REST Assured for HTTP assertions. TestContainers spins up PostgreSQL automatically.
- **Unit tests** (`*Test.java`): standard JUnit 5 + Mockito, no Quarkus context needed for use-case and model tests.
- **Architecture tests** (`arch/`): ArchUnit rules checking layer dependencies, naming conventions, coding rules (no field injection, no generic exceptions).
- **Test profile** (`application-test.yaml`): disables Flyway, uses `drop-and-create` schema generation.
- `IntegrationTest` base class: after each test, runs `flyway.clean()` + `flyway.migrate()` to reset DB state.
- Mother pattern: `MotherCreator.random()` returns a Faker instance for generating test data.

## Database

- Flyway migrations: `src/main/resources/db/migration/V<timestamp>__<name>.sql`
- New migration: `make new-migration` (interactive prompt)
- `schema.sql` at root is generated, not hand-edited (in `.gitignore`)
- TestContainers uses `postgres:16`; local compose uses `postgres:15` — version mismatch is intentional/safe

## Configuration

- Main: `src/main/resources/application.yaml`
- Dev overrides: `application-dev.yaml` (disables auth, CORS for localhost:5173)
- Test overrides: `application-test.yaml`
- API root path: `/api`
- Swagger UI: `/api/q/swagger-ui`
- `MEMRISE_TOKEN` env var required for Memrise sync cron job

## Deployment

- Triggered on version tag push (`*.*.*`)
- Builds native binary with GraalVM via SDKMAN, pushes Docker image, deploys to k3s via Helm
- Version management: `make upgrade-major-version`, `make upgrade-minor-version`, `make upgrade-patch-version` (auto-commits and pushes)
- Tag: `make tag`

## Gotchas

- Dev mode disables security (`quarkus.security.auth.enabled-in-dev-mode: false`). Never assume auth works in `quarkus:dev`.
- Tests use `@TestSecurity` to mock JWT roles; integration tests specify exact roles like `words#create`, `words#update`, `words#publish`.
- Lombok is used — entities/DTOs use `@Data`, `@Getter`, `@Builder` etc. Generated methods exist without source.
- `compose.yaml` (not `docker-compose.yaml`) — the Makefile `start-db`/`stop-db` targets use `docker-compose` (legacy binary), but `README.md` uses `docker compose`. Either works.
- `.env` is in `.gitignore` but still tracked by git (contains `MEMRISE_TOKEN` and `OPENAI_API_KEY`). Run `git rm --cached .env` to untrack without deleting.
