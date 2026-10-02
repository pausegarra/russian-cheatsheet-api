package es.pausegarra.russian_cheatsheet.context.words.application;

import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.ExampleTranslationInputDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.ExampleTranslationEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class ExampleTranslationMapper {

  private ExampleTranslationMapper() {}

  public static List<ExampleTranslationEntity> fromInputs(List<ExampleTranslationInputDto> translations) {
    if (translations == null) {
      return List.of();
    }

    List<ExampleTranslationEntity> result = new ArrayList<>();
    for (int index = 0; index < translations.size(); index++) {
      ExampleTranslationInputDto translation = translations.get(index);
      if (translation.text() == null || translation.text().isBlank()) {
        continue;
      }
      if (translation.language() == null || translation.language().isBlank()) {
        throw new BadRequest("Example translation language is required");
      }
      result.add(new ExampleTranslationEntity(
        translation.language().toLowerCase(Locale.ROOT),
        translation.text(),
        translation.position() == null ? index : translation.position()
      ));
    }
    return List.copyOf(result);
  }
}
