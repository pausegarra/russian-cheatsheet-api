package es.pausegarra.russkiy_po_moyemu.vocabulary.application.services.find_paginated_words;

import es.pausegarra.russkiy_po_moyemu.common.application.pagination.Paginated;
import es.pausegarra.russkiy_po_moyemu.common.application.pagination.PaginatedDto;
import es.pausegarra.russkiy_po_moyemu.common.domain.audit.AuditFields;
import es.pausegarra.russkiy_po_moyemu.vocabulary.application.dto.WordDto;
import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.criterias.WordsSearchCriteria;
import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.entities.WordEntity;
import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.enums.WordTypes;
import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.repositories.WordsRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class FindAllWordsPageInfoServiceTest {

  @InjectMock
  WordsRepository wordsRepository;

  @Inject
  FindAllWordsPaginatedService findAllWordsPaginatedService;

  @Test
  public void shouldFindAllWords() {
    WordEntity word = new WordEntity(
        UUID.randomUUID(),
        "a",
        "a",
        "a",
        WordTypes.VERB,
        new AuditFields(Instant.now(), Instant.now())
    );

    Paginated<WordEntity> paginated = new Paginated<>(
        List.of(word),
        0,
        10,
        1,
        1,
        true,
        false
    );
    when(wordsRepository.findByCriteria(any(WordsSearchCriteria.class)))
        .thenReturn(paginated);

    PaginatedDto<WordDto> words = findAllWordsPaginatedService.handle(FindAllWordsPaginatedQuery.from(0, 10, "spanish", "ASC"));

    List<WordDto> wordsDto = words.data();
    assertEquals(1, wordsDto.size());
    assertEquals("a", wordsDto.getFirst().russian());
    assertEquals("a", wordsDto.getFirst().english());
    assertEquals("a", wordsDto.getFirst().spanish());
    assertEquals("VERB", wordsDto.getFirst().type());
    assertEquals(0, words.page());
    assertEquals(10, words.pageSize());
    assertEquals(1, words.totalPages());
    assertEquals(1, words.totalElements());
    assertTrue(words.hasNextPage());
    assertFalse(words.hasPreviousPage());

    verify(wordsRepository, times(1)).findByCriteria(any(WordsSearchCriteria.class));
  }

}