package es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;
import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.requests.find_words_by_criteria.FindWordsByCriteriaDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.responses.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.presentations.WordPresentation;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindWordsByCriteriaControllerTest {

  @Mock
  private UseCase<FindWordsByCriteriaDto, PaginatedDto<WordDto>> useCase;

  @InjectMocks
  private FindWordsByCriteriaController findWordsByCriteriaController;

  @Test
  void shouldFindWordsByCriteria() {
    WordEntity word = WordMother.random().build();
    Paginated<WordEntity> paginated = new Paginated<>(List.of(word), 0, 10, 1, 1, true, false);

    PaginatedDto<WordDto> paginatedResponse = PaginatedDto.fromPaginated(paginated, List.of(WordDto.fromEntity(word)));
    when(useCase.handle(any(FindWordsByCriteriaDto.class))).thenReturn(paginatedResponse);

    RestResponse<PaginatedDto<WordPresentation>> result = findWordsByCriteriaController.findWordsByCriteria(0, 10, "sortBy", "ASC", "search");

    assertNotNull(result);
    assertEquals(1, result.getEntity().data().size());
    assertEquals(word.id(), result.getEntity().data().getFirst().id());
    assertEquals(0, result.getEntity().page());
    assertEquals(10, result.getEntity().pageSize());
    assertEquals(1, result.getEntity().totalPages());
    assertEquals(1, result.getEntity().totalElements());
    assertTrue(result.getEntity().hasNextPage());
    assertFalse(result.getEntity().hasPreviousPage());
  }

}