package es.pausegarra.russian_cheatsheet.vocabulary.application.services.update_word;

import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.enums.WordTypes;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.exception.WordNotFound;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.repositories.WordsRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@QuarkusTest
class UpdateWordServiceTest {

  @InjectMock
  WordsRepository wordsRepository;

  @Inject
  UpdateWordService updateWordService;

  @Test
  public void shouldUpdateWord() {
    WordEntity word = WordEntity.create(
      UUID.randomUUID(),
      "word",
      "english",
      "spanish",
      WordTypes.VERB
    );

    when(wordsRepository.findById(any(UUID.class))).thenReturn(Optional.of(word));
    when(wordsRepository.save(any(WordEntity.class))).thenReturn(word);

    UpdateWordDto dto = UpdateWordDto.from(
      UUID.randomUUID()
        .toString(), "word", "english", "spanish", "VERB", null, null
    );
    updateWordService.handle(dto);

    verify(wordsRepository).save(any(WordEntity.class));
  }

  @Test
  public void shouldThrowExceptionWhenWordNotFound() {
    when(wordsRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

    UpdateWordDto dto = UpdateWordDto.from(
      UUID.randomUUID()
        .toString(), "word", "english", "spanish", "VERB", null, null
    );
    assertThrows(WordNotFound.class, () -> updateWordService.handle(dto));
  }

}