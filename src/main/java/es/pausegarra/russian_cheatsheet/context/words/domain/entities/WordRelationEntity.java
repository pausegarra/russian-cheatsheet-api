package es.pausegarra.russian_cheatsheet.context.words.domain.entities;

import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordRelationType;

import java.util.UUID;

public record WordRelationEntity(
  UUID sourceWordId,
  UUID relatedWordId,
  WordRelationType relation
) {}
