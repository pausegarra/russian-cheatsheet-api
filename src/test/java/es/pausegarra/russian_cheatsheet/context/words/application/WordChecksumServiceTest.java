package es.pausegarra.russian_cheatsheet.context.words.application;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordAspect;
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
  void shouldIgnoreIdsAndAuditTimestamps() {
    WordEntity firstWord = word(UUID.randomUUID(), "external-word-1", Instant.parse("2026-01-01T00:00:00Z"));
    WordEntity secondWord = word(UUID.randomUUID(), "external-word-2", Instant.parse("2026-02-01T00:00:00Z"));

    assertEquals(
      checksumService.calculate(firstWord),
      checksumService.calculate(secondWord)
    );
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
}
