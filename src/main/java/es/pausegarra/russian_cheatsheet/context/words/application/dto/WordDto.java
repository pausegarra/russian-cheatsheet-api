package es.pausegarra.russian_cheatsheet.context.words.application.dto;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import lombok.Builder;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Builder
public record WordDto(
  UUID id,
  String russian,
  String english,
  String spanish,
  WordType type,
  Instant publishedAt,
  WordConjugationDto conjugations,
  WordDeclinationDto declinations,
  WordDeclinationMatrixDto declinationMatrix,
  String createdBy,
  Instant createdAt,
  String updatedBy,
  Instant updatedAt
) {

  public static WordDto fromEntity(WordEntity entity) {
    WordConjugationDto conjugationsDto = Optional.ofNullable(entity.conjugations())
      .map(WordConjugationDto::fromEntity)
      .orElse(null);
    WordDeclinationDto declinationsDto = Optional.ofNullable(entity.declinations())
      .map(WordDeclinationDto::fromEntity)
      .orElse(null);
    WordDeclinationMatrixDto declinationMatrixDto = Optional.ofNullable(entity.declinationMatrix())
      .map(WordDeclinationMatrixDto::fromEntity)
      .orElse(null);

    return WordDto.builder()
      .id(entity.id())
      .russian(entity.russian())
      .english(entity.english())
      .spanish(entity.spanish())
      .publishedAt(entity.publishedAt())
      .type(entity.type())
      .conjugations(conjugationsDto)
      .declinations(declinationsDto)
      .declinationMatrix(declinationMatrixDto)
      .createdBy(entity.createdBy())
      .createdAt(entity.createdAt())
      .updatedBy(entity.updatedBy())
      .updatedAt(entity.updatedAt())
      .build();
  }

}
