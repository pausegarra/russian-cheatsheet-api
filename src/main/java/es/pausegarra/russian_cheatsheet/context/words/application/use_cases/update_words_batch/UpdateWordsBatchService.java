package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_words_batch;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;
import es.pausegarra.russian_cheatsheet.context.words.application.WordChecksumService;
import es.pausegarra.russian_cheatsheet.context.words.application.WordRelationsService;
import es.pausegarra.russian_cheatsheet.context.words.application.WordWriteMapper;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.RelatedWordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_word.UpdateWordDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.WordNotFound;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class UpdateWordsBatchService implements UseCase<List<UpdateWordDto>, List<WordDto>> {

  private final WordsRepository wordsRepository;
  private final WordWriteMapper wordWriteMapper;
  private final WordChecksumService checksumService;
  private final WordRelationsService relationsService;

  @Override
  @Transactional
  public List<WordDto> handle(List<UpdateWordDto> dtos) {
    List<UUID> ids = validateAndGetIds(dtos);
    Map<UUID, WordEntity> existingWords = indexById(
      wordsRepository.findAllByIdsForUpdate(ids.stream().sorted().toList())
    );
    for (UUID id : ids) {
      if (!existingWords.containsKey(id)) {
        throw new WordNotFound(id.toString());
      }
    }

    List<WordEntity> updates = dtos.stream()
      .map(dto -> wordWriteMapper.fromUpdate(existingWords.get(dto.id()), dto))
      .map(this::withChecksumIfImported)
      .toList();
    Map<UUID, WordEntity> savedWords = indexById(wordsRepository.save(updates));
    Map<UUID, List<RelatedWordDto>> relations = relationsService.findOutgoingWordDtosByIds(ids);

    return dtos.stream()
      .map(dto -> WordDto.fromEntity(savedWords.get(dto.id()), relations.getOrDefault(dto.id(), List.of())))
      .toList();
  }

  private List<UUID> validateAndGetIds(List<UpdateWordDto> dtos) {
    if (dtos == null || dtos.isEmpty()) {
      throw new BadRequest("Batch must contain at least one item");
    }
    Set<UUID> uniqueIds = new HashSet<>();
    for (UpdateWordDto dto : dtos) {
      if (dto == null || dto.id() == null) {
        throw new BadRequest("Every word update must include an id");
      }
      if (!uniqueIds.add(dto.id())) {
        throw new BadRequest("Batch cannot update the same word more than once");
      }
    }
    return dtos.stream().map(UpdateWordDto::id).toList();
  }

  private Map<UUID, WordEntity> indexById(List<WordEntity> words) {
    Map<UUID, WordEntity> result = new HashMap<>();
    words.forEach(word -> result.put(word.id(), word));
    return result;
  }

  private WordEntity withChecksumIfImported(WordEntity word) {
    return word.externalId() == null ? word : word.withChecksum(checksumService.calculate(word));
  }
}
