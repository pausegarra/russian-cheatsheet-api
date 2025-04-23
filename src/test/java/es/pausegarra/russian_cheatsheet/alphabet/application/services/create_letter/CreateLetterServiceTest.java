package es.pausegarra.russian_cheatsheet.alphabet.application.services.create_letter;

import es.pausegarra.russian_cheatsheet.alphabet.domain.entities.LetterEntity;
import es.pausegarra.russian_cheatsheet.alphabet.domain.repositories.LetterRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@QuarkusTest
class CreateLetterServiceTest {

  @InjectMock
  LetterRepository letterRepository;

  @Inject
  CreateLetterService createLetterService;

  @Test
  public void shouldCreateLetter() {
    CreateLetterDto command = CreateLetterDto.from("a", "a", "a");
    doNothing().when(letterRepository)
      .save(any(LetterEntity.class));

    createLetterService.handle(command);

    verify(letterRepository, times(1)).save(any(LetterEntity.class));
  }

  @Test
  public void shouldThrowExceptionIfCommandIsInvalid() {
    CreateLetterDto command = CreateLetterDto.from(null, "a", "a");

    assertThrows(ValidationException.class, () -> createLetterService.handle(command));
  }

}