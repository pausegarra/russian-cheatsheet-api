package es.pausegarra.russian_cheatsheet.context.words.infrastructure.models;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record ExampleTranslationJson(
  String language,
  String text,
  int position
) {}
