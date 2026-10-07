package es.pausegarra.russian_cheatsheet.context.words.application.dto;

import java.util.UUID;

public record WordRelationBatchResultDto(
  UUID wordId,
  boolean created,
  WordRelationDto relation
) {}
