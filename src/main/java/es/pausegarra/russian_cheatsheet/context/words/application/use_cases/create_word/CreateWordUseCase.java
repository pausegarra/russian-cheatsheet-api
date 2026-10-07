package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.WordChecksumService;
import es.pausegarra.russian_cheatsheet.context.words.application.WordRelationsService;
import es.pausegarra.russian_cheatsheet.context.words.application.WordWriteMapper;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class CreateWordUseCase implements UseCase<CreateWordDto, WordDto> {

  private final WordsRepository wordsRepository;
  private final WordRelationsService relationsService;
  private final WordChecksumService checksumService;
  private final WordWriteMapper wordWriteMapper;

  @Transactional
  @Override
  public WordDto handle(CreateWordDto dto) {
    WordEntity word = wordWriteMapper.fromCreate(dto);
    if (word.externalId() != null) {
      word = word.withChecksum(checksumService.calculate(word));
    }
    WordEntity created = wordsRepository.create(word);
    return WordDto.fromEntity(created, relationsService.findOutgoingWordDtos(created.id()));
  }
}
