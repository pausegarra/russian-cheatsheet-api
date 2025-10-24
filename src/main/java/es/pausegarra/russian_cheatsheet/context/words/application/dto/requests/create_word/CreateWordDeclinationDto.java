package es.pausegarra.russian_cheatsheet.context.words.application.dto.requests.create_word;

public record CreateWordDeclinationDto(
  String nominative,
  String accusative,
  String genitive,
  String dative,
  String instrumental,
  String prepositional
) {}
