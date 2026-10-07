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
  List<UUID> linkedWordIds,
  String createdBy,
  Instant createdAt,
  String updatedBy,
  Instant updatedAt
) {

  public ExampleSentenceEntity {
    translations = translations == null ? List.of() : List.copyOf(translations);
    linkedWordIds = linkedWordIds == null ? List.of() : linkedWordIds.stream().distinct().sorted().toList();
  }

  public static ExampleSentenceEntity create(
    String russian,
    List<ExampleTranslationEntity> translations,
    String contributor,
    String audioUrl,
    List<UUID> linkedWordIds
  ) {
    return create(null, russian, translations, contributor, audioUrl, linkedWordIds);
  }

  public static ExampleSentenceEntity create(
    String externalId,
    String russian,
    List<ExampleTranslationEntity> translations,
    String contributor,
    String audioUrl,
    List<UUID> linkedWordIds
  ) {
    return new ExampleSentenceEntity(
      UUID.randomUUID(), externalId, russian, translations, contributor, audioUrl, null,
      linkedWordIds, null, null, null, null
    );
  }

  public ExampleSentenceEntity replaceContent(
    String russian,
    List<ExampleTranslationEntity> translations,
    String contributor,
    String audioUrl,
    List<UUID> linkedWordIds
  ) {
    return new ExampleSentenceEntity(
      id, externalId, russian, translations, contributor, audioUrl, checksum,
      linkedWordIds, createdBy, createdAt, updatedBy, updatedAt
    );
  }

  public ExampleSentenceEntity withChecksum(String checksum) {
    return new ExampleSentenceEntity(
      id, externalId, russian, translations, contributor, audioUrl, checksum,
      linkedWordIds, createdBy, createdAt, updatedBy, updatedAt
    );
  }

}
