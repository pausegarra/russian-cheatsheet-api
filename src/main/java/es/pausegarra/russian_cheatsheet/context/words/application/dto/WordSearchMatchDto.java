package es.pausegarra.russian_cheatsheet.context.words.application.dto;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordSearchResultEntity;

public record WordSearchMatchDto(String kind, String language, String text) {

  public static WordSearchMatchDto fromEntity(WordSearchResultEntity.Match match) {
    return new WordSearchMatchDto(match.kind(), match.language(), match.text());
  }
}
