package es.pausegarra.russian_cheatsheet.vocabulary.application.dto;

import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordEntity;

import java.time.Instant;
import java.util.UUID;

public record WordDto(
  UUID id, String russian, String english, String spanish, String type, ConjugationDto conjugations, Instant createdAt, Instant updatedAt
) {

  public static WordDto fromEntity(WordEntity entity) {
    ConjugationDto conjugations = null;

    if (entity.getConjugations() != null) {
      conjugations = ConjugationDto.from(entity.getConjugations());
    }

    return new WordDto(
      entity.getId(), entity.getRussian(), entity.getEnglish(), entity.getSpanish(), entity.getType()
      .toString(), conjugations, entity.getAuditFields()
        .getCreatedAt(), entity.getAuditFields()
        .getUpdatedAt()
    );
  }

}
