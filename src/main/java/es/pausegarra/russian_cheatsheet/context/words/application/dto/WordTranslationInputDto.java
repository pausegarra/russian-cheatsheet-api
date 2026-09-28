package es.pausegarra.russian_cheatsheet.context.words.application.dto;

public record WordTranslationInputDto(
  String language,
  String text,
  Integer position
) {}
