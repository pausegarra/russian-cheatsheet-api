package es.pausegarra.russian_cheatsheet.context.words.application;

import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.RelatedWordDto;
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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class WordRelationsService {

  private final WordRelationsRepository relationsRepository;
  private final WordsRepository wordsRepository;
  private final WordChecksumService checksumService;

  @Transactional
  public CreateWordRelationResultDto createOutgoingAndRefresh(
    UUID sourceWordId,
    WordRelationInputDto requestedRelation
  ) {
    if (requestedRelation == null) {
      throw new BadRequest("Relation target and type are required");
    }

    WordRelationWriteResult result = relationsRepository.createOutgoing(
      new WordRelationEntity(sourceWordId, requestedRelation.relatedWordId(), requestedRelation.relation())
    );
    if (result.created()) {
      refreshChecksums(List.of(sourceWordId));
    }
    return new CreateWordRelationResultDto(result.created(), WordRelationDto.fromEntity(result.relation()));
  }

  @Transactional
  public void deleteOutgoingAndRefresh(UUID sourceWordId, UUID relationId) {
    relationsRepository.deleteOutgoing(sourceWordId, relationId);
    refreshChecksums(List.of(sourceWordId));
  }

  @Transactional
  public void refreshChecksums(Collection<UUID> wordIds) {
    wordIds.stream().distinct().forEach(wordId -> wordsRepository.findById(wordId).ifPresent(word -> {
      if (word.externalId() == null) {
        return;
      }
      List<RelatedWordEntity> outgoingRelations = relationsRepository.findOutgoing(wordId);
      wordsRepository.save(word.withChecksum(checksumService.calculate(word, outgoingRelations)));
    }));
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

  private List<RelatedWordEntity> findOutgoing(UUID wordId) {
    wordsRepository.findById(wordId).orElseThrow(() -> new WordNotFound(wordId.toString()));
    return relationsRepository.findOutgoing(wordId);
  }

}
