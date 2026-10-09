package es.pausegarra.russian_cheatsheet.context.words.domain.entities;

import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordRelationType;

import java.util.UUID;

public record WordRelationDetailsEntity(
  UUID id,
  UUID sourceWordId,
  String sourceExternalId,
  WordEntity relatedWord,
  WordRelationType relation,
  String checksum
) {}
