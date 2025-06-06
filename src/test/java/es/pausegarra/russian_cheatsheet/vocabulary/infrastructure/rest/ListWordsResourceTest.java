package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;
import es.pausegarra.russian_cheatsheet.common.infrastructure.presentations.PaginatedPresentation;
import es.pausegarra.russian_cheatsheet.mother.words.entities.WordEntityMother;
import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.vocabulary.application.services.find_paginated_words.FindAllWordsPaginatedDto;
import es.pausegarra.russian_cheatsheet.vocabulary.application.services.find_paginated_words.FindAllWordsPaginatedService;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.presentations.WordListPresentation;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
class ListWordsResourceTest {

  @InjectMock
  FindAllWordsPaginatedService findAllWordsPaginatedService;

  @Inject
  ListWordsResource listWordsResource;

  @Test
  public void shouldReturnAllWords() {
    WordEntity word = WordEntityMother.random()
      .build();
    WordDto wordDto = WordDto.fromEntity(word);
    PaginatedDto<WordDto> paginatedDto = new PaginatedDto<>(
      List.of(wordDto),
      0,
      10,
      1,
      1,
      true,
      false
    );

    when(findAllWordsPaginatedService.handle(any(FindAllWordsPaginatedDto.class))).thenReturn(
      paginatedDto);

    RestResponse<PaginatedPresentation<WordListPresentation>> response =
      listWordsResource.listWords(0,
      10,
      "spanish",
      "asc",
      ""
    );

    assertEquals(200, response.getStatus());

    verify(findAllWordsPaginatedService, times(1)).handle(any(FindAllWordsPaginatedDto.class));
  }

}