package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.delete_word_relation;

import java.util.UUID;

public record DeleteWordRelationDto(
  UUID sourceWordId,
  UUID relationId
) {}
