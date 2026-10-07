package es.pausegarra.russian_cheatsheet.context.words.infrastructure.requests;

import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordRelationType;

import java.util.UUID;

public record WordRelationBatchRequest(
  UUID wordId,
  UUID relatedWordId,
  WordRelationType relation
) {}
