package es.pausegarra.russian_cheatsheet.context.words.domain.entities;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ExampleSentenceEntity(
  UUID id,
  String externalId,
  String russian,
  List<ExampleTranslationEntity> translations,
  String contributor,
  String audioUrl,
  String checksum,
  List<String> linkedWordExternalIds,
  String createdBy,
  Instant createdAt,
  String updatedBy,
  Instant updatedAt
) {

  public ExampleSentenceEntity {
    translations = translations == null ? List.of() : List.copyOf(translations);
    linkedWordExternalIds = linkedWordExternalIds == null ? List.of() : linkedWordExternalIds.stream().distinct().sorted().toList();
  }

  public static ExampleSentenceEntity create(
    String externalId,
    String russian,
    List<ExampleTranslationEntity> translations,
    String contributor,
    String audioUrl,
    List<String> linkedWordExternalIds
  ) {
    return new ExampleSentenceEntity(
      UUID.randomUUID(), externalId, russian, translations, contributor, audioUrl, null,
      linkedWordExternalIds, null, null, null, null
    );
  }

  public ExampleSentenceEntity replaceSourceData(
    String russian,
    List<ExampleTranslationEntity> translations,
    String contributor,
    String audioUrl,
    List<String> linkedWordExternalIds
  ) {
    return new ExampleSentenceEntity(
      id, externalId, russian, translations, contributor, audioUrl, checksum,
      linkedWordExternalIds, createdBy, createdAt, updatedBy, updatedAt
    );
  }

  public ExampleSentenceEntity withChecksum(String checksum) {
    return new ExampleSentenceEntity(
      id, externalId, russian, translations, contributor, audioUrl, checksum,
      linkedWordExternalIds, createdBy, createdAt, updatedBy, updatedAt
    );
  }

}
