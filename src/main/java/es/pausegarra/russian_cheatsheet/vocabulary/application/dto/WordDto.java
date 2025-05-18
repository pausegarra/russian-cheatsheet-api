package es.pausegarra.russian_cheatsheet.vocabulary.application.dto;

import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordEntity;

import java.time.Instant;
import java.util.UUID;

public record WordDto(
  UUID id,
  String russian,
  String english,
  String spanish,
  String type,
  WordConjugationsDto conjugations,
  WordCaseDto cases,
  Instant createdAt,
  Instant updatedAt
) {

  public static WordDto fromEntity(WordEntity entity) {
    WordConjugationsDto conjugations = null;
    WordCaseDto cases = null;

    if (entity.getConjugations() != null) {
      conjugations = WordConjugationsDto.fromEntity(entity.getConjugations());
    }

    if (entity.getCases() != null) {
      cases = WordCaseDto.fromEntity(entity.getCases());
    }

    return new WordDto(
      entity.getId(),
      entity.getRussian(),
      entity.getEnglish(),
      entity.getSpanish(),
      entity.getType()
        .toString(),
      conjugations,
      cases,
      entity.getAuditFields()
        .getCreatedAt(),
      entity.getAuditFields()
        .getUpdatedAt()
    );
  }

}
