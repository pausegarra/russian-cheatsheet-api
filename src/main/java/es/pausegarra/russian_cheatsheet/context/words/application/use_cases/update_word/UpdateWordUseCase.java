package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_word;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.WordChecksumService;
import es.pausegarra.russian_cheatsheet.context.words.application.WordRelationsService;
import es.pausegarra.russian_cheatsheet.context.words.application.WordWriteMapper;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.WordNotFound;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class UpdateWordUseCase implements UseCase<UpdateWordDto, WordDto> {

  private final WordsRepository wordsRepository;
  private final WordRelationsService relationsService;
  private final WordChecksumService checksumService;
  private final WordWriteMapper wordWriteMapper;

  @Override
  @Transactional
  public WordDto handle(UpdateWordDto dto) {
    WordEntity word = wordsRepository.findById(dto.id())
      .orElseThrow(() -> new WordNotFound(dto.id().toString()));
    WordEntity updated = wordWriteMapper.fromUpdate(word, dto);

    if (updated.externalId() != null) {
      updated = updated.withChecksum(checksumService.calculate(updated));
    }
    WordEntity saved = wordsRepository.save(updated);
    return WordDto.fromEntity(saved, relationsService.findOutgoingWordDtos(saved.id()));
  }
}
