package es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;
import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.find_unpublished.FindUnpublishedWordsDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.presentations.ListWordsPresentation;
import es.pausegarra.russian_cheatsheet.mother.WordMother;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindUnpublishedWordsResourceTest {

  @Mock
  private UseCase<FindUnpublishedWordsDto, PaginatedDto<WordDto>> findUnpublishedWordsUseCase;

  @InjectMocks
  private FindUnpublishedWordsResource findUnpublishedWordsResource;

  @Test
  void shouldReturnUnpublishedWords() {
    WordDto word = WordDto.fromEntity(WordMother.random().build());
    PaginatedDto<WordDto> paginatedDto = new PaginatedDto<>(
      List.of(word),
      0,
      1,
      1,
      2,
      true,
      true
    );
    when(findUnpublishedWordsUseCase.handle(any(FindUnpublishedWordsDto.class))).thenReturn(paginatedDto);

    RestResponse<PaginatedDto<ListWordsPresentation>> response = findUnpublishedWordsResource.findPublishedWords(
      0,
      1,
      "russian",
      "ASC",
      ""
    );

    assertNotNull(response);
    assertEquals(200, response.getStatus());

    List<ListWordsPresentation> presentations = response.getEntity().data();
    assertNotNull(presentations);
    assertEquals(1, presentations.size());
    assertEquals(word.russian(), presentations.getFirst().russian());

    verify(findUnpublishedWordsUseCase).handle(any(FindUnpublishedWordsDto.class));
  }

}