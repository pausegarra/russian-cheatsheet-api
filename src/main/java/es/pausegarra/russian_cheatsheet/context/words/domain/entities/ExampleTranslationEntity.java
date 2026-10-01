package es.pausegarra.russian_cheatsheet.context.words.domain.entities;

public record ExampleTranslationEntity(
  String language,
  String text,
  int position
) {}
