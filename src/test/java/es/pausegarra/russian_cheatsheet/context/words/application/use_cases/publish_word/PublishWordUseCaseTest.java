package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.publish_word;

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
class PublishWordUseCaseTest {

  @Mock
  private WordsRepository wordsRepository;

  @InjectMocks
  private PublishWordUseCase publishWordUseCase;

  @Test
  public void shouldPublishWord() {
    WordEntity word = WordMother.random().publishedAt(null).build();
    when(wordsRepository.findById(any(UUID.class))).thenReturn(Optional.of(word));
    when(wordsRepository.save(any(WordEntity.class))).thenReturn(word);

    PublishWordDto dto = new PublishWordDto(word.id());
    publishWordUseCase.handle(dto);

    verify(wordsRepository).findById(any(UUID.class));
    verify(wordsRepository).save(any(WordEntity.class));
  }

  @Test
  public void shouldThrowExceptionWhenPublishingWordThatDoesNotExist() {
    when(wordsRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

    PublishWordDto dto = new PublishWordDto(UUID.randomUUID());
    assertThrows(WordNotFound.class, () -> publishWordUseCase.handle(dto));
  }

}