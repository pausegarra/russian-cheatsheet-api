package es.pausegarra.russian_cheatsheet.vocabulary.application.services.find_by_id;

import es.pausegarra.russian_cheatsheet.common.domain.audit.AuditFields;
import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@QuarkusTest
class FindWordByIdServiceTest {

  @InjectMock
  WordsRepository wordsRepository;

  @Inject
  FindWordByIdService findWordByIdService;

  @Test
  public void shouldFindWordById() {
    WordEntity word = new WordEntity(null, "a", "a", "a", WordTypes.VERB, null, new AuditFields());

    when(wordsRepository.findById(any(UUID.class))).thenReturn(Optional.of(word));

    FindWordByIdDto dto = FindWordByIdDto.from(UUID.randomUUID()
      .toString());
    WordDto wordDto = findWordByIdService.handle(dto);

    assertEquals(word.getId(), wordDto.id());
    assertEquals(word.getRussian(), wordDto.russian());
    assertEquals(word.getEnglish(), wordDto.english());
    assertEquals(word.getSpanish(), wordDto.spanish());
    assertEquals(
      word.getType()
        .toString(), wordDto.type()
    );

    verify(wordsRepository, times(1)).findById(any(UUID.class));
  }

  @Test
  public void shouldThrowExceptionWhenWordIsNotFound() {
    when(wordsRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

    FindWordByIdDto dto = FindWordByIdDto.from(UUID.randomUUID()
      .toString());
    assertThrows(WordNotFound.class, () -> findWordByIdService.handle(dto));
  }

}