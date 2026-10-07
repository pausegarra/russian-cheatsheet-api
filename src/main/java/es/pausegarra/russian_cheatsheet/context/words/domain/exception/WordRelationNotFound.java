package es.pausegarra.russian_cheatsheet.context.words.domain.exception;

import es.pausegarra.russian_cheatsheet.common.domain.exception.NotFound;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordRelationEntity;

import java.util.UUID;

public class WordRelationNotFound extends NotFound {

  public WordRelationNotFound(UUID relationId) {
    super("Word relation with id " + relationId + " not found");
  }

  public WordRelationNotFound(WordRelationEntity relation) {
    super("Word relation from " + relation.sourceWordId() + " to " + relation.relatedWordId()
      + " with type " + relation.relation().value() + " not found");
  }

}
