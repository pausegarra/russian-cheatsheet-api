package es.pausegarra.russian_cheatsheet.vocabulary.application.services.create_word;

import es.pausegarra.russian_cheatsheet.mother.words.entities.WordEntityMother;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.exception.WordAlreadyExists;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.repositories.WordsRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@QuarkusTest
class CreateWordServiceTest {

  @InjectMock
  WordsRepository wordsRepository;

  @Inject
  CreateWordService createWordService;

  @Test
  public void shouldCreateWord() {
    WordEntity word = WordEntityMother.random()
      .build();
    when(wordsRepository.save(any(WordEntity.class))).thenReturn(word);

    createWordService.handle(CreateWordDto.from("a", "a", "a", "VERB", null, null));

    verify(wordsRepository, times(2)).save(any(WordEntity.class));
  }

  @Test
  public void shouldThrowExceptionIfCommandIsInvalid() {
    CreateWordDto command = CreateWordDto.from(null, null, null, "VERB", null, null);

    assertThrows(ValidationException.class, () -> createWordService.handle(command));
  }

  @Test
  public void shouldThrowExceptionIfWordExists() {
    when(wordsRepository.findByRussian(anyString())).thenReturn(Optional.of(WordEntityMother.random()
        .build()));

    CreateWordDto command = CreateWordDto.from("a", "a", "a", "VERB", null, null);
    assertThrows(WordAlreadyExists.class, () -> createWordService.handle(command));
  }

}