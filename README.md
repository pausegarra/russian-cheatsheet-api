# Russian Cheatsheet

Backend service for managing a Russian vocabulary catalogue, exposing CRUD-style word endpoints and publication workflows.

Built with Quarkus, Java 21, Maven, PostgreSQL, Flyway, and Keycloak-based JWT authentication.

## What It Does

- Stores Russian words with translations and grammatical metadata.
- Stores source-native OpenRussian forms for nouns, verbs, adjectives, and pronouns in one `forms` object.
- Exposes public read endpoints for listing and fetching words.
- Exposes protected write endpoints for creating, updating, publishing, and reviewing unpublished words.

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

## Local Setup

1. Start PostgreSQL:

```sh
docker compose up -d
```

2. Run the app:

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
- `GET /api/examples`
- `GET /api/words/{wordId}/examples`

Protected endpoints:

- `POST /api/words`
- `PUT /api/words/{wordId}`
- `POST /api/examples`
- `PUT /api/examples/{id}`
- `PATCH /api/words/{wordId}/publish`
- `GET /api/words/unpublished`
- `GET /api/auth/profile/permissions`

Role requirements on protected word endpoints:

- `words#create`
- `words#update`
- `words#publish`

Role requirements on protected example endpoints:

- `examples#create`
- `examples#update`

The external process uses existing `POST /api/words` for new words and `PUT /api/words/{wordId}` for changes; requests carry `externalId` and `translations[]`. New imported words are published immediately because source entries are already public. Requests must omit checksums. Each translation has language, text, and position. If position is omitted, API uses entry's array index. Repeated positions within one language and owner are moved to the next available position, preserving duplicate translations without violating the storage key. `translations[]` is the only translation representation; the API replaces imported translations while preserving manually managed translations, including Spanish (`language: "es"`).

OpenRussian word relations use `related`, `synonym`, and `antonym`. Create one directed row with `POST /api/words/{wordId}/relations` and body `{ "relatedWordId": "<API word UUID>", "relation": "related" }`. Each POST creates one direction; send a second POST with reversed source and target to create a mutual pair. An identical POST returns the existing relation row. `GET /api/words/{wordId}/relations` lists outgoing rows as `{id, relatedWordId, russian, relation}`, where `id` is the relation-row UUID. `DELETE /api/words/{wordId}/relations/{relationId}` removes only that directed row; change a relation by deleting and creating it again. Word POST/PUT requests do not manage relations. Relation source and target must be existing imported words with `externalId`; manual words cannot own or be targets of imported relations. Word detail keeps `relatedWords` for compatibility and returns outgoing edges only; existing edges receive inverse rows during migration. The paginated word list stays relation-free.

Word `type` uses the exact OpenRussian values: `noun`, `pronoun`, `verb`, `adjective`, `adverb`, `expression`, and `other`. Imported words may have `type: null` when their OpenRussian source type is blank; manual words still require a type. Such imported words cannot include forms or aspect. Morphology is sent and returned as one `forms` object whose keys are the original `form_type` names (for example `ru_verb_presfut_sg1`, `ru_verb_gerund_present`, `ru_noun_sg_gen`, or `ru_adj_m_nom`). The API rejects noun, verb, and adjective form fields that do not match the word's type; `adjective` and `pronoun` share the `ru_adj_*` forms. `ru_base` is supported for every type. Verb `aspect` is a root-level value (`imperfective`, `perfective`, or `both`); imported verbs may leave it null when OpenRussian has no aspect, while manual verbs still require it.

`GET /api/words` returns paginated published words with `id`, `externalId`, and `checksum`, which intake uses to select `POST` versus `PUT`. Example endpoints use API UUIDs: `POST /api/examples` creates an example and returns its generated `id`; `PUT /api/examples/{id}` updates an existing example; `GET /api/words/{wordId}/examples` returns linked examples with `linkedWordIds`.

Word detail and list responses include `checksum`. Example sentence responses include their own `checksum` and linked word UUIDs. Checksums are lowercase SHA-256 hex over compact UTF-8 JSON with property names sorted alphabetically and null values included. Word checksums cover Russian text, type, aspect, imported translations sorted by `(language, text)`, usage, audio URL, and the complete `forms` object. Translation positions and relations are excluded, so relation operations do not change word checksums. Manual translations, generated UUIDs, audit fields, publication state, `externalId`, and the checksum itself are also excluded. Existing imported checksums are cleared once by migration so the intake can recompute them with this projection. Example checksums cover Russian text, translations, contributor, audio URL, and sorted linked word UUIDs. Missing translation positions use original array index; missing example link arrays normalize to `[]`. Preserve Unicode text and stress marks when reproducing hashes.

`POST /api/examples` accepts Russian text, translations, contributor, audio URL, and `linkedWordIds` as API word UUIDs. `PUT /api/examples/{id}` uses the example's API UUID; a missing or empty `linkedWordIds` list clears links. `GET /api/words/{wordId}/examples` is paginated.

## Imported data attribution

OpenRussian dictionary data is licensed CC BY-SA 4.0. Preserve attribution when using the imported word and sentence data. Example records retain contributor attribution; audio is stored as a URL only, since the audio files are not part of the open dataset.

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
