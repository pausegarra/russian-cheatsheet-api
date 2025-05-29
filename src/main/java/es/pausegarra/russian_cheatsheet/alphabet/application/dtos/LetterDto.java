package es.pausegarra.russian_cheatsheet.alphabet.application.dtos;

import es.pausegarra.russian_cheatsheet.alphabet.domain.entities.LetterEntity;

import java.time.Instant;
import java.util.UUID;

public record LetterDto(
  UUID id, String latin, String ipa, String cyrillic, Instant createdAt, Instant updatedAt
) {

  public static LetterDto fromEntity(LetterEntity entity) {
    return new LetterDto(
      entity.getId(),
      entity.getLatin(),
      entity.getIpa(),
      entity.getCyrillic(),
      entity.getAuditFields()
        .getCreatedAt(),
      entity.getAuditFields()
        .getUpdatedAt()
    );
  }

}
