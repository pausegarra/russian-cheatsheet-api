package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.requests;

public record CreateWordRequest(
  String russian, String english, String spanish, String type, WordRequestConjugations conjugations
) {}
