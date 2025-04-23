package es.pausegarra.russian_cheatsheet.vocabulary.application.services.find_paginated_words;

import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;
import es.pausegarra.russian_cheatsheet.common.domain.audit.AuditFields;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.criterias.WordsSearchCriteria;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.enums.WordTypes;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.repositories.WordsRepository;
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
      UUID.randomUUID(), "a", "a", "a", WordTypes.VERB, new AuditFields(Instant.now(), Instant.now()));

    Paginated<WordEntity> paginated = new Paginated<>(List.of(word), 0, 10, 1, 1, true, false);
    when(wordsRepository.findByCriteria(any(WordsSearchCriteria.class))).thenReturn(paginated);

    PaginatedDto<WordDto> words = findAllWordsPaginatedService.handle(
      FindAllWordsPaginatedDto.from(0, 10, "spanish", "ASC"));

    List<WordDto> wordsDto = words.data();
    assertEquals(1, wordsDto.size());
    assertEquals(
      "a", wordsDto.getFirst()
        .russian()
    );
    assertEquals(
      "a", wordsDto.getFirst()
        .english()
    );
    assertEquals(
      "a", wordsDto.getFirst()
        .spanish()
    );
    assertEquals(
      "VERB", wordsDto.getFirst()
        .type()
    );
    assertEquals(0, words.page());
    assertEquals(10, words.pageSize());
    assertEquals(1, words.totalPages());
    assertEquals(1, words.totalElements());
    assertTrue(words.hasNextPage());
    assertFalse(words.hasPreviousPage());

    verify(wordsRepository, times(1)).findByCriteria(any(WordsSearchCriteria.class));
  }

}