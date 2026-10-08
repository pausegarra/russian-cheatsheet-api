package es.pausegarra.russian_cheatsheet.context.words.domain.repositories;

import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.RelatedWordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordRelationDetailsEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordRelationEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordRelationWriteResult;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface WordRelationsRepository {

  WordRelationWriteResult createOutgoing(WordRelationEntity relation, String checksum);

  List<WordRelationWriteResult> createOutgoingBatch(List<WordRelationEntity> relations, List<String> checksums);

  List<RelatedWordEntity> findOutgoing(UUID sourceWordId);

  Map<UUID, List<RelatedWordEntity>> findOutgoingBySourceIds(List<UUID> sourceWordIds);

  Paginated<WordRelationDetailsEntity> findAll(int page, int perPage);

  void deleteOutgoing(UUID sourceWordId, UUID relationId);

  void deleteOutgoingBatch(List<WordRelationEntity> relations);
}
