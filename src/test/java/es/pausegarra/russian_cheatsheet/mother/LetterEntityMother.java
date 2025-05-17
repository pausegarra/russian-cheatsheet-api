package es.pausegarra.russian_cheatsheet.mother;

import es.pausegarra.russian_cheatsheet.alphabet.domain.entities.LetterEntity;
import es.pausegarra.russian_cheatsheet.common.domain.audit.AuditFields;

import java.util.UUID;

public class LetterEntityMother {

  public static LetterEntity.LetterEntityBuilder random() {
    return LetterEntity.builder()
      .id(UUID.randomUUID())
      .cyrillic(MotherCreator.random()
        .name()
        .toString())
      .latin(MotherCreator.random()
        .name()
        .toString())
      .ipa(MotherCreator.random()
        .name()
        .toString())
      .auditFields(new AuditFields());
  }

}
