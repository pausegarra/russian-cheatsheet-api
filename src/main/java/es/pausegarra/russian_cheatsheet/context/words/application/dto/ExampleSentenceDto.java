package es.pausegarra.russian_cheatsheet.context.words.application.dto;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.ExampleSentenceEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.ImportedExampleReferenceEntity;
import lombok.Builder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Builder
public record ExampleSentenceDto(
  UUID id,
  String externalId,
  String russian,
  List<ExampleTranslationDto> translations,
  String contributor,
  String audioUrl,
  String checksum,
  List<UUID> linkedWordIds,
  Instant createdAt,
  Instant updatedAt
) {

  public static ExampleSentenceDto fromEntity(ExampleSentenceEntity entity) {
    return ExampleSentenceDto.builder()
      .id(entity.id())
      .externalId(entity.externalId())
      .russian(entity.russian())
      .translations(entity.translations().stream().map(ExampleTranslationDto::fromEntity).toList())
      .contributor(entity.contributor())
      .audioUrl(entity.audioUrl())
      .checksum(entity.checksum())
      .linkedWordIds(entity.linkedWordIds())
      .createdAt(entity.createdAt())
      .updatedAt(entity.updatedAt())
      .build();
  }

  public static ExampleSentenceDto fromImportReference(ImportedExampleReferenceEntity entity) {
    return ExampleSentenceDto.builder()
      .id(entity.id())
      .externalId(entity.externalId())
      .checksum(entity.checksum())
      .build();
  }

}
