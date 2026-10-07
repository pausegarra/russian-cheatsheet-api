package es.pausegarra.russian_cheatsheet.context.words.application.use_cases;

import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;
import es.pausegarra.russian_cheatsheet.context.words.application.WordChecksumService;
import es.pausegarra.russian_cheatsheet.context.words.application.WordRelationsService;
import es.pausegarra.russian_cheatsheet.context.words.application.WordWriteMapper;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word.CreateWordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_words_batch.CreateWordsBatchService;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_word.UpdateWordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_words_batch.UpdateWordsBatchService;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WordsBatchServiceTest {

  @Mock
  WordsRepository wordsRepository;

  @Mock
  WordWriteMapper wordWriteMapper;

  @Mock
  WordChecksumService checksumService;

  @Mock
  WordRelationsService relationsService;

  @Test
  void createUsesBulkRepositoryAndReturnsInputOrder() {
    when(wordWriteMapper.fromCreate(any(CreateWordDto.class))).thenAnswer(call -> {
      CreateWordDto dto = call.getArgument(0);
      return WordEntity.createManual(dto.russian(), List.of(), null, null, WordType.OTHER, null, null);
    });
    when(wordsRepository.create(anyList())).thenAnswer(call -> call.getArgument(0));
    CreateWordsBatchService service = new CreateWordsBatchService(
      wordsRepository, wordWriteMapper, checksumService
    );

    List<WordDto> result = service.handle(List.of(createDto("первый"), createDto("второй")));

    assertEquals(List.of("первый", "второй"), result.stream().map(WordDto::russian).toList());
    verify(wordsRepository, times(1)).create(anyList());
  }

  @Test
  void updateRejectsRepeatedIdsBeforeSaving() {
    UpdateWordDto request = updateDto(UUID.randomUUID(), "слово");
    UpdateWordsBatchService service = new UpdateWordsBatchService(
      wordsRepository, wordWriteMapper, checksumService, relationsService
    );

    assertThrows(BadRequest.class, () -> service.handle(List.of(request, request)));
    verify(wordsRepository, never()).save(anyList());
  }

  private CreateWordDto createDto(String russian) {
    return new CreateWordDto(russian, null, List.of(), null, null, WordType.OTHER, null, null);
  }

  private UpdateWordDto updateDto(UUID id, String russian) {
    return new UpdateWordDto(id, russian, null, null, null, null, WordType.OTHER, null, null);
  }
}
