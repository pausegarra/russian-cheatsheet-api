package es.pausegarra.russian_cheatsheet.context.words.application.use_cases;

import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;
import es.pausegarra.russian_cheatsheet.context.words.application.ExampleChecksumService;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.ExampleSentenceDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_example.CreateExampleDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_examples_batch.CreateExamplesBatchService;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_example.UpdateExampleDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_examples_batch.UpdateExamplesBatchService;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.ExampleNotFound;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.ExampleSentencesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExamplesBatchServiceTest {

  @Mock
  ExampleSentencesRepository repository;

  @Mock
  ExampleChecksumService checksumService;

  @Test
  void createUsesBulkRepositoryAndReturnsInputOrder() {
    when(repository.create(anyList())).thenAnswer(call -> call.getArgument(0));
    CreateExamplesBatchService service = new CreateExamplesBatchService(repository, checksumService);

    List<ExampleSentenceDto> result = service.handle(List.of(createDto("первый"), createDto("второй")));

    assertEquals(List.of("первый", "второй"), result.stream().map(ExampleSentenceDto::russian).toList());
    verify(repository, times(1)).create(anyList());
  }

  @Test
  void updateDoesNotSaveWhenAnExampleIsMissing() {
    UUID missingId = UUID.randomUUID();
    when(repository.findAllByIds(anyList())).thenReturn(List.of());
    UpdateExamplesBatchService service = new UpdateExamplesBatchService(repository, checksumService);

    assertThrows(ExampleNotFound.class, () -> service.handle(List.of(updateDto(missingId))));
    verify(repository, never()).save(anyList());
  }

  @Test
  void updateRejectsRepeatedIdsBeforeLoading() {
    UUID id = UUID.randomUUID();
    UpdateExampleDto request = updateDto(id);
    UpdateExamplesBatchService service = new UpdateExamplesBatchService(repository, checksumService);

    assertThrows(BadRequest.class, () -> service.handle(List.of(request, request)));
    verify(repository, never()).findAllByIds(anyList());
  }

  private CreateExampleDto createDto(String russian) {
    return new CreateExampleDto(null, russian, List.of(), null, null, List.of());
  }

  private UpdateExampleDto updateDto(UUID id) {
    return new UpdateExampleDto(id, "дом", List.of(), null, null, List.of());
  }
}
