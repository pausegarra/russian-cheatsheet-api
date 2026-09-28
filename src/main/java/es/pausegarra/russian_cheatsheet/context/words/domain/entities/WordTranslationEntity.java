package es.pausegarra.russian_cheatsheet.context.words.domain.entities;

import es.pausegarra.russian_cheatsheet.context.words.domain.enums.TranslationOrigin;

public record WordTranslationEntity(
  String language,
  String text,
  TranslationOrigin managedBy,
  int position
) {}
