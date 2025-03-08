package es.pausegarra.russkiy_po_moyemu.alphabet.application.services.create_letter;

import es.pausegarra.russkiy_po_moyemu.alphabet.domain.entities.LetterEntity;
import es.pausegarra.russkiy_po_moyemu.alphabet.domain.repositories.LetterRepository;
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
    CreateLetterCommand command = CreateLetterCommand.from("a", "a");
    doNothing().when(letterRepository).save(any(LetterEntity.class));

    createLetterService.handle(command);

    verify(letterRepository, times(1)).save(any(LetterEntity.class));
  }

  @Test
  public void shouldThrowExceptionIfCommandIsInvalid() {
    CreateLetterCommand command = CreateLetterCommand.from(null, "a");

    assertThrows(ValidationException.class, () -> createLetterService.handle(command));
  }

}