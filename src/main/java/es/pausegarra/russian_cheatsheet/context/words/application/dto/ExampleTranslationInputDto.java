package es.pausegarra.russian_cheatsheet.context.words.application.dto;

public record ExampleTranslationInputDto(
  String language,
  String text,
  Integer position
) {}
