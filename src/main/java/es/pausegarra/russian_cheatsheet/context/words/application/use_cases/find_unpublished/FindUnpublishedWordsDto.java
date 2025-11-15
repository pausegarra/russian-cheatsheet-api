package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.find_unpublished;

public record FindUnpublishedWordsDto(
  int page,
  int perPage,
  String sortBy,
  String sortDirection,
  String search
) {}
