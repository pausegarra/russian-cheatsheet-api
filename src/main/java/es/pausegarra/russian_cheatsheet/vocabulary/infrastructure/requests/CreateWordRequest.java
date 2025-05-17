package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.requests;

import lombok.Builder;

@Builder
public record CreateWordRequest(
  String russian, String english, String spanish, String type, WordRequestConjugations conjugations
) {}
