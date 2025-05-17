package es.pausegarra.russian_cheatsheet.vocabulary.application.services.create_word;

import es.pausegarra.russian_cheatsheet.common.domain.audit.AuditFields;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.enums.WordTypes;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.exception.WordAlreadyExists;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.repositories.WordsRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

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
    WordEntity word = new WordEntity(
      UUID.randomUUID(),
      "a",
      "a",
      "a",
      WordTypes.VERB,
      null,
      new AuditFields(Instant.now(), Instant.now())
    );
    when(wordsRepository.save(any(WordEntity.class))).thenReturn(word);

    createWordService.handle(CreateWordDto.from("a", "a", "a", "VERB", null));

    verify(wordsRepository, times(2)).save(any(WordEntity.class));
  }

  @Test
  public void shouldThrowExceptionIfCommandIsInvalid() {
    CreateWordDto command = CreateWordDto.from(null, null, null, "VERB", null);

    assertThrows(ValidationException.class, () -> createWordService.handle(command));
  }

  @Test
  public void shouldThrowExceptionIfWordExists() {
    when(wordsRepository.findByRussian(anyString())).thenReturn(Optional.of(new WordEntity(
      UUID.randomUUID(),
      "a",
      "a",
      "a",
      WordTypes.VERB,
      null,
      new AuditFields(Instant.now(), Instant.now())
    )));

    CreateWordDto command = CreateWordDto.from("a", "a", "a", "VERB", null);
    assertThrows(WordAlreadyExists.class, () -> createWordService.handle(command));
  }

}