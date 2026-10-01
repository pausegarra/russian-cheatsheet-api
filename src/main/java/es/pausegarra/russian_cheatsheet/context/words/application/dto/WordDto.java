package es.pausegarra.russian_cheatsheet.context.words.application.dto;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordFormsEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordAspect;
import lombok.Builder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Builder
public record WordDto(
  UUID id,
  String externalId,
  String russian,
  List<WordTranslationDto> translations,
  String usage,
  String audioUrl,
  String checksum,
  WordType type,
  WordAspect aspect,
  Instant publishedAt,
  WordFormsEntity forms,
  List<RelatedWordDto> relatedWords,
  String createdBy,
  Instant createdAt,
  String updatedBy,
  Instant updatedAt
) {

  public static WordDto fromEntity(WordEntity entity) {
    return fromEntity(entity, List.of());
  }

  public static WordDto fromEntity(WordEntity entity, List<RelatedWordDto> relatedWords) {
    return WordDto.builder()
      .id(entity.id())
      .externalId(entity.externalId())
      .russian(entity.russian())
      .translations(entity.translations().stream().map(WordTranslationDto::fromEntity).toList())
      .usage(entity.usage())
      .audioUrl(entity.audioUrl())
      .checksum(entity.checksum())
      .publishedAt(entity.publishedAt())
      .type(entity.type())
      .aspect(entity.aspect())
      .forms(entity.forms())
      .relatedWords(relatedWords)
      .createdBy(entity.createdBy())
      .createdAt(entity.createdAt())
      .updatedBy(entity.updatedBy())
      .updatedAt(entity.updatedAt())
      .build();
  }

}
