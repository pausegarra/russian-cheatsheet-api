package es.pausegarra.russian_cheatsheet.mother;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordTranslationEntity;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordTranslationDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordTranslationInputDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.TranslationOrigin;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class WordMother {

  public static List<WordTranslationInputDto> translationInputs(WordEntity word) {
    return word.translations().stream()
      .map(translation -> new WordTranslationInputDto(translation.language(), translation.text(), translation.position()))
      .toList();
  }

  public static List<WordTranslationInputDto> translationInputs(String english, String spanish) {
    return List.of(
      new WordTranslationInputDto("en", english, 0),
      new WordTranslationInputDto("es", spanish, 1)
    );
  }

  public static List<WordTranslationDto> translationDtos(WordEntity word) {
    return word.translations().stream().map(WordTranslationDto::fromEntity).toList();
  }

  public static WordEntity.WordEntityBuilder random() {
    return WordEntity.builder()
      .id(UUID.randomUUID())
      .russian(MotherCreator.random().animal().name())
      .translations(List.of(
        new WordTranslationEntity("en", MotherCreator.random().animal().name(), TranslationOrigin.MANUAL, 0),
        new WordTranslationEntity("es", MotherCreator.random().animal().name(), TranslationOrigin.MANUAL, 0)
      ))
      .type(WordType.NOUN)
      .publishedAt(MotherCreator.random().date().past(10, TimeUnit.DAYS).toInstant())
      .createdBy(MotherCreator.random().name().username())
      .createdAt(MotherCreator.random().date().past(10, TimeUnit.DAYS).toInstant())
      .updatedBy(MotherCreator.random().name().username())
      .updatedAt(MotherCreator.random().date().past(10, TimeUnit.DAYS).toInstant());
  }

}
