package es.pausegarra.russian_cheatsheet.context.words.application;

import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.RelatedWordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordRelationBatchInputDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordRelationBatchResultDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordRelationDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordRelationInputDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word_relation.CreateWordRelationResultDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.RelatedWordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordRelationDetailsEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordRelationEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordRelationWriteResult;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.WordNotFound;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordRelationsRepository;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
@RequiredArgsConstructor
public class WordRelationsService {

  private final WordRelationsRepository relationsRepository;
  private final WordsRepository wordsRepository;
  private final WordRelationChecksumService checksumService;

  @Transactional
  public CreateWordRelationResultDto createOutgoing(
    UUID sourceWordId,
    WordRelationInputDto requestedRelation
  ) {
    if (requestedRelation == null) {
      throw new BadRequest("Relation target and type are required");
    }

    if (sourceWordId == null || requestedRelation.relatedWordId() == null || requestedRelation.relation() == null) {
      throw new BadRequest("Relation source, target, and type are required");
    }

    WordRelationEntity relation = new WordRelationEntity(
      sourceWordId, requestedRelation.relatedWordId(), requestedRelation.relation()
    );
    Map<UUID, WordEntity> wordsById = findWordsByIdsForUpdate(
      List.of(sourceWordId, requestedRelation.relatedWordId())
    );
    String checksum = calculateChecksum(relation, wordsById);
    WordRelationWriteResult result = relationsRepository.createOutgoing(relation, checksum);
    return new CreateWordRelationResultDto(result.created(), WordRelationDto.fromEntity(result.relation()));
  }

  public void deleteOutgoing(UUID sourceWordId, UUID relationId) {
    relationsRepository.deleteOutgoing(sourceWordId, relationId);
  }

  @Transactional
  public List<WordRelationBatchResultDto> createOutgoingBatch(List<WordRelationBatchInputDto> relations) {
    List<WordRelationEntity> entities = relations.stream()
      .map(relation -> new WordRelationEntity(relation.wordId(), relation.relatedWordId(), relation.relation()))
      .toList();
    List<UUID> wordIds = entities.stream()
      .flatMap(relation -> Stream.of(relation.sourceWordId(), relation.relatedWordId()))
      .filter(Objects::nonNull)
      .distinct()
      .sorted()
      .toList();
    Map<UUID, WordEntity> wordsById = findWordsByIdsForUpdate(wordIds);
    List<String> checksums = entities.stream().map(relation -> calculateChecksum(relation, wordsById)).toList();
    List<WordRelationWriteResult> results = relationsRepository.createOutgoingBatch(entities, checksums);
    List<WordRelationBatchResultDto> batchResults = new ArrayList<>(results.size());
    for (int index = 0; index < results.size(); index++) {
      WordRelationWriteResult result = results.get(index);
      batchResults.add(new WordRelationBatchResultDto(
        relations.get(index).wordId(), result.created(), WordRelationDto.fromEntity(result.relation())
      ));
    }
    return List.copyOf(batchResults);
  }

  public void deleteOutgoingBatch(List<WordRelationBatchInputDto> relations) {
    relationsRepository.deleteOutgoingBatch(relations.stream()
      .map(relation -> new WordRelationEntity(relation.wordId(), relation.relatedWordId(), relation.relation()))
      .toList());
  }

  public List<WordRelationDto> findOutgoingDtos(UUID wordId) {
    return findOutgoing(wordId).stream()
      .map(WordRelationDto::fromEntity)
      .toList();
  }

  public List<RelatedWordDto> findOutgoingWordDtos(UUID wordId) {
    return findOutgoing(wordId).stream()
      .map(RelatedWordDto::fromEntity)
      .toList();
  }

  public Map<UUID, List<RelatedWordDto>> findOutgoingWordDtosByIds(List<UUID> wordIds) {
    return relationsRepository.findOutgoingBySourceIds(wordIds).entrySet().stream()
      .collect(Collectors.toUnmodifiableMap(
        Map.Entry::getKey,
        entry -> entry.getValue().stream().map(RelatedWordDto::fromEntity).toList()
      ));
  }

  public Paginated<WordRelationDetailsEntity> findAll(int page, int perPage) {
    return relationsRepository.findAll(page, perPage);
  }

  private List<RelatedWordEntity> findOutgoing(UUID wordId) {
    wordsRepository.findById(wordId).orElseThrow(() -> new WordNotFound(wordId.toString()));
    return relationsRepository.findOutgoing(wordId);
  }

  private Map<UUID, WordEntity> findWordsByIdsForUpdate(List<UUID> wordIds) {
    Map<UUID, WordEntity> wordsById = new LinkedHashMap<>();
    wordsRepository.findAllByIdsForUpdate(wordIds).forEach(word -> wordsById.put(word.id(), word));
    return wordsById;
  }

  private String calculateChecksum(WordRelationEntity relation, Map<UUID, WordEntity> wordsById) {
    WordEntity source = wordsById.get(relation.sourceWordId());
    if (source == null) {
      throw new WordNotFound(String.valueOf(relation.sourceWordId()));
    }
    WordEntity related = wordsById.get(relation.relatedWordId());
    if (related == null) {
      throw new WordNotFound(String.valueOf(relation.relatedWordId()));
    }
    return checksumService.calculate(source.russian(), related.russian(), relation.relation());
  }

}
