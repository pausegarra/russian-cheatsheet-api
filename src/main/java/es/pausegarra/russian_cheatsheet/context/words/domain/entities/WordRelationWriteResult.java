package es.pausegarra.russian_cheatsheet.context.words.domain.entities;

public record WordRelationWriteResult(
  boolean created,
  RelatedWordEntity relation
) {}
