package es.pausegarra.russian_cheatsheet.context.words.domain.entities;

import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordRelationType;

import java.util.UUID;

public record WordRelationChecksumEntity(
  UUID id,
  String sourceRussian,
  String relatedRussian,
  WordRelationType relation
) {}
