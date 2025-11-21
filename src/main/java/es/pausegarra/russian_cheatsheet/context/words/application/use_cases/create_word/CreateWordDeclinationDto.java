package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word;

public record CreateWordDeclinationDto(
  String nominative,
  String accusative,
  String genitive,
  String dative,
  String instrumental,
  String prepositional,
  String nominativePlural,
  String accusativePlural,
  String genitivePlural,
  String dativePlural,
  String instrumentalPlural,
  String prepositionalPlural
) {}
