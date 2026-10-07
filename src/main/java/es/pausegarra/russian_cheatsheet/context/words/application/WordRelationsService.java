package es.pausegarra.russian_cheatsheet.context.words.application;

import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.RelatedWordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordRelationBatchInputDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordRelationBatchResultDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordRelationDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordRelationInputDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word_relation.CreateWordRelationResultDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.RelatedWordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordRelationEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordRelationWriteResult;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.WordNotFound;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordRelationsRepository;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
@RequiredArgsConstructor
public class WordRelationsService {

  private final WordRelationsRepository relationsRepository;
  private final WordsRepository wordsRepository;

  public CreateWordRelationResultDto createOutgoing(
    UUID sourceWordId,
    WordRelationInputDto requestedRelation
  ) {
    if (requestedRelation == null) {
      throw new BadRequest("Relation target and type are required");
    }

    WordRelationWriteResult result = relationsRepository.createOutgoing(
      new WordRelationEntity(sourceWordId, requestedRelation.relatedWordId(), requestedRelation.relation())
    );
    return new CreateWordRelationResultDto(result.created(), WordRelationDto.fromEntity(result.relation()));
  }

  public void deleteOutgoing(UUID sourceWordId, UUID relationId) {
    relationsRepository.deleteOutgoing(sourceWordId, relationId);
  }

  public List<WordRelationBatchResultDto> createOutgoingBatch(List<WordRelationBatchInputDto> relations) {
    List<WordRelationEntity> entities = relations.stream()
      .map(relation -> new WordRelationEntity(relation.wordId(), relation.relatedWordId(), relation.relation()))
      .toList();
    List<WordRelationWriteResult> results = relationsRepository.createOutgoingBatch(entities);
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

  private List<RelatedWordEntity> findOutgoing(UUID wordId) {
    wordsRepository.findById(wordId).orElseThrow(() -> new WordNotFound(wordId.toString()));
    return relationsRepository.findOutgoing(wordId);
  }

}
