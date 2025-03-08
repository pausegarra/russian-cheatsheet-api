package es.pausegarra.russkiy_po_moyemu.alphabet.application.dtos;

import es.pausegarra.russkiy_po_moyemu.alphabet.domain.entities.LetterEntity;

import java.time.Instant;
import java.util.UUID;

public record LetterDto(
    UUID id,
    String letter,
    String ipa,
    Instant createdAt,
    Instant updatedAt
) {

  public static LetterDto fromEntity(LetterEntity entity) {
    return new LetterDto(
        entity.getId(),
        entity.getLetter(),
        entity.getIpa(),
        entity.getAuditFields().getCreatedAt(),
        entity.getAuditFields().getUpdatedAt()
    );
  }

}
