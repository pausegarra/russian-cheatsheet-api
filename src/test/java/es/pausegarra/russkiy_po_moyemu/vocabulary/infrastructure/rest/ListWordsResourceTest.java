package es.pausegarra.russkiy_po_moyemu.vocabulary.infrastructure.rest;

import es.pausegarra.russkiy_po_moyemu.common.application.pagination.PaginatedDto;
import es.pausegarra.russkiy_po_moyemu.vocabulary.application.dto.WordDto;
import es.pausegarra.russkiy_po_moyemu.vocabulary.application.services.find_paginated_words.FindAllWordsPaginatedQuery;
import es.pausegarra.russkiy_po_moyemu.vocabulary.application.services.find_paginated_words.FindAllWordsPaginatedService;
import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.entities.WordEntity;
import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.enums.WordTypes;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
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
    WordEntity word = WordEntity.create(
        null,
        "a",
        "a",
        "a",
        WordTypes.VERB
    );
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

    when(findAllWordsPaginatedService.handle(any(FindAllWordsPaginatedQuery.class)))
        .thenReturn(paginatedDto);

    Response response = listWordsResource.listWords(
        0,
        10,
        "spanish",
        "asc"
    );

    assertEquals(200, response.getStatus());

    verify(findAllWordsPaginatedService, times(1)).handle(any(FindAllWordsPaginatedQuery.class));
  }

}