package es.pausegarra.russian_cheatsheet.context.words.application.use_cases;

import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.find_words_by_criteria.FindWordsByCriteriaDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.find_words_by_criteria.FindWordsByCriteriaUseCase;
import es.pausegarra.russian_cheatsheet.context.words.domain.criterias.WordSearchCriteria;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordsRepository;
import es.pausegarra.russian_cheatsheet.mother.WordMother;
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
class FindWordsByCriteriaUseCaseTest {

  @Mock
  private WordsRepository wordsRepository;

  @InjectMocks
  private FindWordsByCriteriaUseCase findWordsByCriteriaUseCase;

  @Test
  public void shouldHandleWords() {
    WordEntity word = WordMother.random().build();
    Paginated<WordEntity> paginated = new Paginated<>(List.of(word), 0, 10, 1, 1, true, false);
    when(wordsRepository.findByCriteria(any(WordSearchCriteria.class))).thenReturn(paginated);

    FindWordsByCriteriaDto dto = new FindWordsByCriteriaDto(0, 10, "sortBy", "ASC", "search");
    PaginatedDto<WordDto> result = findWordsByCriteriaUseCase.handle(dto);

    assertNotNull(result);
    assertEquals(1, result.data().size());
    assertEquals(word.id(), result.data().getFirst().id());
    assertEquals(0, result.page());
    assertEquals(10, result.pageSize());
    assertEquals(1, result.totalPages());
    assertEquals(1, result.totalElements());
    assertTrue(result.hasNextPage());
    assertFalse(result.hasPreviousPage());

    verify(wordsRepository).findByCriteria(any(WordSearchCriteria.class));
  }

}