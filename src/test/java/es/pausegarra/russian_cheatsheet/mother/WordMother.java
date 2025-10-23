package es.pausegarra.russian_cheatsheet.mother;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class WordMother {

  public static WordEntity.WordEntityBuilder random() {
    return WordEntity.builder()
      .id(UUID.randomUUID())
      .russian(MotherCreator.random().animal().name())
      .english(MotherCreator.random().animal().name())
      .spanish(MotherCreator.random().animal().name())
      .type(WordType.NOUN)
      .createdBy(MotherCreator.random().name().username())
      .createdAt(MotherCreator.random().date().past(10, TimeUnit.DAYS).toInstant())
      .updatedBy(MotherCreator.random().name().username())
      .updatedAt(MotherCreator.random().date().past(10, TimeUnit.DAYS).toInstant());
  }

}
