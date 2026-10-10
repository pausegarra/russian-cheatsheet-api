package es.pausegarra.russian_cheatsheet.context.words.infrastructure.models;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordTranslationEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.TranslationOrigin;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record WordTranslationJson(
  String language,
  String text,
  TranslationOrigin managedBy,
  int position
) {

  public static WordTranslationJson fromEntity(WordTranslationEntity entity) {
    return new WordTranslationJson(entity.language(), entity.text(), entity.managedBy(), entity.position());
  }

  public WordTranslationEntity toEntity() {
    return new WordTranslationEntity(language, text, managedBy, position);
  }
}
