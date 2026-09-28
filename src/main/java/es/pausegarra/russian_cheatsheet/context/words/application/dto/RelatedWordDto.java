package es.pausegarra.russian_cheatsheet.context.words.application.dto;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.RelatedWordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordRelationType;

import java.util.UUID;

public record RelatedWordDto(
  UUID id,
  String russian,
  WordRelationType relation
) {

  public static RelatedWordDto fromEntity(RelatedWordEntity entity) {
    return new RelatedWordDto(entity.id(), entity.russian(), entity.relation());
  }
}
