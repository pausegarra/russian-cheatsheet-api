package es.pausegarra.russian_cheatsheet.context.words.domain.entities;

import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import lombok.Builder;
import lombok.With;

import java.time.Instant;
import java.util.UUID;

@Builder
public record WordEntity(
  UUID id,
  String russian,
  String english,
  String spanish,
  String createdBy,
  WordType type,
  @With WordConjugationEntity conjugations,
  @With WordDeclinationEntity declinations,
  @With WordDeclinationMatrixEntity declinationMatrix,
  Instant createdAt,
  String updatedBy,
  Instant updatedAt
) {}
