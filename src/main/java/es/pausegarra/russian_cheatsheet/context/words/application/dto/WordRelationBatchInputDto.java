package es.pausegarra.russian_cheatsheet.context.words.application.dto;

import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordRelationType;

import java.util.UUID;

public record WordRelationBatchInputDto(
  UUID wordId,
  UUID relatedWordId,
  WordRelationType relation
) {}
