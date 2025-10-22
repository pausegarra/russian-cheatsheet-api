package es.pausegarra.russian_cheatsheet.context.words.domain.entities;

import java.time.Instant;
import java.util.UUID;

public record WordEntity(
  UUID id,
  String russian,
  String english,
  String spanish,
  String createdBy,
  Instant createdAt,
  String updatedBy,
  Instant updatedAt
) {}
