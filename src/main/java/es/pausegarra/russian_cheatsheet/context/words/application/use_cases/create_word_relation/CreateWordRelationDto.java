package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word_relation;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordRelationInputDto;

import java.util.UUID;

public record CreateWordRelationDto(
  UUID sourceWordId,
  WordRelationInputDto relation
) {}
