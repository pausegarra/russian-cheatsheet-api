package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.find_word_by_id;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.WordNotFound;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordsRepository;
import es.pausegarra.russian_cheatsheet.mother.WordMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindWordByIdUseCaseTest {

  @Mock
  private WordsRepository wordsRepository;

  @InjectMocks
  private FindWordByIdUseCase findWordByIdUseCase;

  @Test
  public void shouldFindWordById() {
    WordEntity word = WordMother.random().build();
    when(wordsRepository.findById(any(UUID.class))).thenReturn(Optional.of(word));

    FindWordByIdDto dto = new FindWordByIdDto(word.id());
    WordDto result = findWordByIdUseCase.handle(dto);

    assertNotNull(result);
    assertEquals(word.id(), result.id());
    assertEquals(word.russian(), result.russian());
    assertEquals(word.english(), result.english());
    assertEquals(word.spanish(), result.spanish());
    assertEquals(word.type(), result.type());
    assertEquals(word.createdBy(), result.createdBy());
    assertEquals(word.createdAt(), result.createdAt());
    assertEquals(word.updatedBy(), result.updatedBy());
    assertEquals(word.updatedAt(), result.updatedAt());
  }

  @Test
  public void shouldThrowExceptionIfWordNotFound() {
    when(wordsRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

    FindWordByIdDto dto = new FindWordByIdDto(UUID.randomUUID());
    assertThrows(WordNotFound.class, () -> findWordByIdUseCase.handle(dto));
  }

}