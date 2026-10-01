package es.pausegarra.russian_cheatsheet.context.words.domain.repositories;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.RelatedWordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordRelationEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordRelationWriteResult;

import java.util.List;
import java.util.UUID;

public interface WordRelationsRepository {

  WordRelationWriteResult createOutgoing(WordRelationEntity relation);

  List<RelatedWordEntity> findOutgoing(UUID sourceWordId);

  void deleteOutgoing(UUID sourceWordId, UUID relationId);

  List<UUID> findIncomingSourceIds(UUID targetWordId);
}
