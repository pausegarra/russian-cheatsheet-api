package es.pausegarra.russian_cheatsheet.context.words.domain.entities;

import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordRelationType;

import java.util.UUID;

public record RelatedWordEntity(
  UUID id,
  UUID relationId,
  String externalId,
  String russian,
  WordRelationType relation
) {}
