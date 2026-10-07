package es.pausegarra.russian_cheatsheet.context.words.domain.entities;

import java.util.UUID;

public record ImportedExampleReferenceEntity(
  UUID id,
  String externalId,
  String checksum
) {}
