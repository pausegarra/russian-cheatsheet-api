package es.pausegarra.russian_cheatsheet.context.words.application.dto;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordDeclinationEntity;

import java.time.Instant;
import java.util.UUID;

public record WordDeclinationDto(
  UUID id,
  WordDto word,
  String nominative,
  String accusative,
  String genitive,
  String dative,
  String instrumental,
  String prepositional,
  String createdBy,
  Instant createdAt,
  String updatedBy,
  Instant updatedAt
) {

  public static WordDeclinationDto fromEntity(WordDeclinationEntity entity, WordDto word) {
    return new WordDeclinationDto(
      entity.id(),
      word,
      entity.nominative(),
      entity.accusative(),
      entity.genitive(),
      entity.dative(),
      entity.instrumental(),
      entity.prepositional(),
      entity.createdBy(),
      entity.createdAt(),
      entity.updatedBy(),
      entity.updatedAt()
    );
  }

}
