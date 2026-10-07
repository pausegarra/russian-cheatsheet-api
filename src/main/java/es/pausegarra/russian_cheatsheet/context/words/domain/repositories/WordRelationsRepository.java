package es.pausegarra.russian_cheatsheet.context.words.domain.repositories;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.RelatedWordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordRelationEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordRelationWriteResult;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface WordRelationsRepository {

  WordRelationWriteResult createOutgoing(WordRelationEntity relation);

  List<WordRelationWriteResult> createOutgoingBatch(List<WordRelationEntity> relations);

  List<RelatedWordEntity> findOutgoing(UUID sourceWordId);

  Map<UUID, List<RelatedWordEntity>> findOutgoingBySourceIds(List<UUID> sourceWordIds);

  void deleteOutgoing(UUID sourceWordId, UUID relationId);

  void deleteOutgoingBatch(List<WordRelationEntity> relations);
}
