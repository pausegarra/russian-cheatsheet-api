package es.pausegarra.russian_cheatsheet.context.words.application.dto.responses;

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
  WordConjugationDto conjugations,
  WordDeclinationDto declinations,
  WordDeclinationMatrixDto declinationMatrix,
  String createdBy,
  Instant createdAt,
  String updatedBy,
  Instant updatedAt
) {

  public static WordDto fromEntity(WordEntity entity) {
    WordDto.WordDtoBuilder builder = WordDto.builder()
      .id(entity.id())
      .russian(entity.russian())
      .english(entity.english())
      .spanish(entity.spanish())
      .type(entity.type())
      .createdBy(entity.createdBy())
      .createdAt(entity.createdAt())
      .updatedBy(entity.updatedBy())
      .updatedAt(entity.updatedAt());

    WordConjugationDto conjugationsDto = Optional.ofNullable(entity.conjugations())
      .map((conjugations) -> WordConjugationDto.fromEntity(conjugations, builder.build()))
      .orElse(null);
    WordDeclinationDto declinationsDto = Optional.ofNullable(entity.declinations())
      .map((declinations) -> WordDeclinationDto.fromEntity(declinations, builder.build()))
      .orElse(null);
    WordDeclinationMatrixDto declinationMatrixDto = Optional.ofNullable(entity.declinationMatrix())
      .map((declinationMatrix) -> WordDeclinationMatrixDto.fromEntity(declinationMatrix, builder.build()))
      .orElse(null);

    builder.conjugations(conjugationsDto)
      .declinations(declinationsDto)
      .declinationMatrix(declinationMatrixDto);

    return builder.build();
  }

}
