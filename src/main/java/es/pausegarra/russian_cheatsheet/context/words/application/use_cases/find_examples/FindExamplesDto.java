package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.find_examples;

import java.util.UUID;

public record FindExamplesDto(
  UUID wordId,
  int page,
  int perPage,
  boolean externalIdOnly
) {}
