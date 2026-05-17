package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.delete_word;

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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteWordUseCaseTest {

  @Mock
  private WordsRepository wordsRepository;

  @InjectMocks
  private DeleteWordUseCase deleteWordUseCase;

  @Test
  public void shouldDeleteWord() {
    WordEntity word = WordMother.random().build();
    when(wordsRepository.findById(any(UUID.class))).thenReturn(Optional.of(word));

    DeleteWordDto dto = new DeleteWordDto(word.id());
    deleteWordUseCase.handle(dto);

    verify(wordsRepository).findById(any(UUID.class));
    verify(wordsRepository).delete(word);
  }

  @Test
  public void shouldThrowExceptionWhenDeletingWordThatDoesNotExist() {
    when(wordsRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

    DeleteWordDto dto = new DeleteWordDto(UUID.randomUUID());
    assertThrows(WordNotFound.class, () -> deleteWordUseCase.handle(dto));
  }

}
