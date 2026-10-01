package es.pausegarra.russian_cheatsheet.context.words.application;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.RelatedWordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordAspect;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordRelationType;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class WordChecksumServiceTest {

  private final WordChecksumService checksumService = new WordChecksumService();

  @Test
  void shouldIgnoreIdsAndSortRelationsByRussianWordAndType() {
    WordEntity firstWord = word(UUID.randomUUID(), "external-word-1", Instant.parse("2026-01-01T00:00:00Z"));
    WordEntity secondWord = word(UUID.randomUUID(), "external-word-2", Instant.parse("2026-02-01T00:00:00Z"));

    List<RelatedWordEntity> firstRelations = List.of(
      related("external-target-2", "слово", WordRelationType.ANTONYM),
      related("external-target-1", "дом", WordRelationType.RELATED),
      related("external-target-3", "дом", WordRelationType.SYNONYM)
    );
    List<RelatedWordEntity> secondRelations = List.of(
      related("different-target-1", "дом", WordRelationType.SYNONYM),
      related("different-target-2", "слово", WordRelationType.ANTONYM),
      related("different-target-3", "дом", WordRelationType.RELATED)
    );

    assertEquals(
      checksumService.calculate(firstWord, firstRelations),
      checksumService.calculate(secondWord, secondRelations)
    );
  }

  @Test
  void shouldChangeChecksumWhenRelationTypeChanges() {
    WordEntity word = word(UUID.randomUUID(), "external-word", Instant.parse("2026-01-01T00:00:00Z"));

    String synonymChecksum = checksumService.calculate(
      word,
      List.of(related("target-1", "дом", WordRelationType.SYNONYM))
    );
    String antonymChecksum = checksumService.calculate(
      word,
      List.of(related("target-1", "дом", WordRelationType.ANTONYM))
    );

    assertNotEquals(synonymChecksum, antonymChecksum);
  }

  @Test
  void shouldChangeChecksumWhenRelatedRussianWordChanges() {
    WordEntity word = word(UUID.randomUUID(), "external-word", Instant.parse("2026-01-01T00:00:00Z"));

    String checksumForHouse = checksumService.calculate(
      word,
      List.of(related("target-1", "дом", WordRelationType.RELATED))
    );
    String checksumForBook = checksumService.calculate(
      word,
      List.of(related("target-1", "книга", WordRelationType.RELATED))
    );

    assertNotEquals(checksumForHouse, checksumForBook);
  }

  private WordEntity word(UUID id, String externalId, Instant timestamp) {
    return WordEntity.builder()
      .id(id)
      .externalId(externalId)
      .russian("говорить")
      .translations(List.of())
      .usage("to speak")
      .audioUrl("https://example.com/audio.mp3")
      .type(WordType.VERB)
      .aspect(WordAspect.IMPERFECTIVE)
      .publishedAt(timestamp)
      .createdBy("importer")
      .createdAt(timestamp)
      .updatedBy("importer")
      .updatedAt(timestamp)
      .build();
  }

  private RelatedWordEntity related(String externalId, String russian, WordRelationType relationType) {
    return new RelatedWordEntity(
      UUID.randomUUID(), UUID.randomUUID(), externalId, russian, relationType
    );
  }
}
