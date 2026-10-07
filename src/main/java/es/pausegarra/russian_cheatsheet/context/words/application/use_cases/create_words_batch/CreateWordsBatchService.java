package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_words_batch;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;
import es.pausegarra.russian_cheatsheet.context.words.application.WordChecksumService;
import es.pausegarra.russian_cheatsheet.context.words.application.WordWriteMapper;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word.CreateWordDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class CreateWordsBatchService implements UseCase<List<CreateWordDto>, List<WordDto>> {

  private final WordsRepository wordsRepository;
  private final WordWriteMapper wordWriteMapper;
  private final WordChecksumService checksumService;

  @Override
  @Transactional
  public List<WordDto> handle(List<CreateWordDto> dtos) {
    validateBatch(dtos);
    List<WordEntity> words = dtos.stream()
      .map(wordWriteMapper::fromCreate)
      .map(this::withChecksumIfImported)
      .toList();
    return wordsRepository.create(words).stream().map(WordDto::fromEntity).toList();
  }

  private WordEntity withChecksumIfImported(WordEntity word) {
    return word.externalId() == null ? word : word.withChecksum(checksumService.calculate(word));
  }

  private void validateBatch(List<CreateWordDto> dtos) {
    if (dtos == null || dtos.isEmpty()) {
      throw new BadRequest("Batch must contain at least one item");
    }
    if (dtos.stream().anyMatch(dto -> dto == null)) {
      throw new BadRequest("Batch items cannot be null");
    }
  }
}
