package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.find_words_by_criteria;

public record FindWordsByCriteriaDto(
  int page, int perPage, String sortBy, String sortDirection, String search
) {}
