package es.pausegarra.russkiy_po_moyemu.vocabulary.application.services.delete_word;

import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.entities.WordEntity;
import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.enums.WordTypes;
import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.exception.WordNotFound;
import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.repositories.WordsRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
class DeleteWordServiceTest {

  @InjectMock
  WordsRepository wordsRepository;

  @Inject
  DeleteWordService deleteWordService;

  @Test
  public void shouldDeleteWord() {
    WordEntity word = WordEntity.create(
      UUID.randomUUID(),
      "Russkiy po moyemu",
      "Russkiy po moyemu",
      "russkiy po moyemu",
      WordTypes.VERB
    );

    when(wordsRepository.findById(any(UUID.class))).thenReturn(Optional.of(word));
    doNothing().when(wordsRepository).delete(any(UUID.class));

    DeleteWordDto dto = DeleteWordDto.from(UUID.randomUUID().toString());
    deleteWordService.handle(dto);

    verify(wordsRepository).delete(any(UUID.class));
  }

  @Test
  public void shouldThrowNotFoundExceptionIfWordNotFound() {
    when(wordsRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

    DeleteWordDto dto = DeleteWordDto.from(UUID.randomUUID().toString());
    assertThrows(WordNotFound.class, () -> deleteWordService.handle(dto));
  }

}