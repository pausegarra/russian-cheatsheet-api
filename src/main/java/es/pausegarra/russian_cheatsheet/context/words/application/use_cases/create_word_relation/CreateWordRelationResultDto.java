package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word_relation;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordRelationDto;

public record CreateWordRelationResultDto(
  boolean created,
  WordRelationDto relation
) {}
