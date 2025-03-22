package es.pausegarra.russkiy_po_moyemu.vocabulary.application.dto;

import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.entities.WordEntity;

import java.time.Instant;
import java.util.UUID;

public record WordDto(
    UUID id,
    String russian,
    String english,
    String spanish,
    String type,
    Instant createdAt,
    Instant updatedAt
) {

  public static WordDto fromEntity(WordEntity entity) {
    return new WordDto(
        entity.getId(),
        entity.getRussian(),
        entity.getEnglish(),
        entity.getSpanish(),
        entity.getType().toString(),
        entity.getAuditFields().getCreatedAt(),
        entity.getAuditFields().getUpdatedAt()
    );
  }

}
