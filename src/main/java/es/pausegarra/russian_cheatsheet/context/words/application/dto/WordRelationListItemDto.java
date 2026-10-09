package es.pausegarra.russian_cheatsheet.context.words.application.dto;

import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordRelationType;

import java.util.UUID;

public record WordRelationListItemDto(
  UUID id,
  UUID sourceWordId,
  String sourceExternalId,
  UUID relatedWordId,
  String relatedExternalId,
  WordDto relatedWord,
  WordRelationType relation,
  String checksum
) {}
