package es.pausegarra.russian_cheatsheet.context.words.application.dto;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.RelatedWordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordRelationType;

import java.util.UUID;

public record WordRelationDto(
  UUID id,
  UUID relatedWordId,
  String russian,
  WordRelationType relation,
  String checksum
) {

  public static WordRelationDto fromEntity(RelatedWordEntity entity) {
    return new WordRelationDto(
      entity.relationId(), entity.id(), entity.russian(), entity.relation(), entity.checksum()
    );
  }

}
