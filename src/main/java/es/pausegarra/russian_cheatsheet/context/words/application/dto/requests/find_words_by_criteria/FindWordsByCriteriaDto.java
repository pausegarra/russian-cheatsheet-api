package es.pausegarra.russian_cheatsheet.context.words.application.dto.requests.find_words_by_criteria;

public record FindWordsByCriteriaDto(
  int page,
  int perPage,
  String sortBy,
  String sortDirection,
  String search
) {}
