package es.pausegarra.russian_cheatsheet.alphabet.application.services.list_letters;

import es.pausegarra.russian_cheatsheet.alphabet.application.dtos.LetterDto;
import es.pausegarra.russian_cheatsheet.alphabet.domain.entities.LetterEntity;
import es.pausegarra.russian_cheatsheet.alphabet.domain.repositories.LetterRepository;
import es.pausegarra.russian_cheatsheet.common.domain.audit.AuditFields;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@QuarkusTest
class ListLettersServiceTest {

  @InjectMock
  LetterRepository letterRepository;

  @Inject
  ListLettersService listLettersService;

  @Test
  public void shouldReturnAllLetters() {
    LetterEntity letter = new LetterEntity(
      UUID.randomUUID(),
      "a",
      "a",
      "a",
      new AuditFields(Instant.now(), Instant.now())
    );

    when(letterRepository.fetchAll()).thenReturn(List.of(letter));

    List<LetterDto> letters = listLettersService.handle(ListLettersDto.from());

    assertEquals(1, letters.size());
    assertEquals(
      "a",
      letters.getFirst()
        .cyrillic()
    );
    assertEquals(
      "a",
      letters.getFirst()
        .ipa()
    );
    assertEquals(
      "a",
      letters.getFirst()
        .latin()
    );

    verify(letterRepository, times(1)).fetchAll();
  }

}