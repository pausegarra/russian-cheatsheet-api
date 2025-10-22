package es.pausegarra.russian_cheatsheet.context.words.domain.entities;

import java.time.Instant;
import java.util.UUID;

public record WordDeclinationEntity(
  UUID id,
  WordEntity word,
  String nominative,
  String accusative,
  String genitive,
  String dative,
  String instrumental,
  String prepositional,
  String createdBy,
  Instant createdAt,
  String updatedBy,
  Instant updatedAt
) {}
