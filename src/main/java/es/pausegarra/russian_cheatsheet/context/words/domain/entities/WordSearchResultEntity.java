package es.pausegarra.russian_cheatsheet.context.words.domain.entities;

public record WordSearchResultEntity(WordEntity word, Match match) {

  public record Match(String kind, String language, String text) {}
}
