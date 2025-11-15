package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.find_unpublished;

import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindUnpublishedWordsUseCaseTest {

  @Mock
  private WordsRepository wordsRepository;

  @InjectMocks
  private FindUnpublishedWordsUseCase findUnpublishedWordsUseCase;

  @Test
  public void shouldReturnUnpublishedWords() {
    WordEntity word = WordMother.random().build();
    Paginated<WordEntity> paginated = new Paginated<>(
      List.of(word),
      0,
      1,
      1,
      2,
      true,
      true
    );
    when(wordsRepository.findByCriteria(any(WordSearchCriteria.class))).thenReturn(paginated);

    FindUnpublishedWordsDto dto = new FindUnpublishedWordsDto(
      0,
      1,
      "id",
      "ASC",
      ""
    );
    PaginatedDto<WordDto> result = findUnpublishedWordsUseCase.handle(dto);

    assertNotNull(result);
    assertEquals(dto.page(), result.page());
    assertEquals(dto.perPage(), result.pageSize());

    WordDto entity = result.data().getFirst();
    assertNotNull(entity);
    assertEquals(word.russian(), entity.russian());
    assertEquals(word.english(), entity.english());
    assertEquals(word.spanish(), entity.spanish());
    assertEquals(word.type(), entity.type());
  }

}