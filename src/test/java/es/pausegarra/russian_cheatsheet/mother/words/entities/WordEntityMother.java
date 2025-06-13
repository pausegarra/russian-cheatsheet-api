package es.pausegarra.russian_cheatsheet.mother.words.entities;

import es.pausegarra.russian_cheatsheet.common.domain.audit.AuditFields;
import es.pausegarra.russian_cheatsheet.mother.MotherCreator;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.enums.WordTypes;

import java.util.UUID;

public class WordEntityMother {

  public static WordEntity.WordEntityBuilder random() {
    return WordEntity.builder()
      .id(UUID.randomUUID())
      .russian(MotherCreator.random()
        .name()
        .toString())
      .english(MotherCreator.random()
        .name()
        .toString())
      .spanish(MotherCreator.random()
        .name()
        .toString())
      .type(MotherCreator.random()
        .options()
        .option(WordTypes.class))
      .conjugations(null)
      .cases(null)
      .auditFields(new AuditFields());
  }

}
