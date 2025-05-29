package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.presentations;

import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordDto;

public record WordListPresentation(
  String id, String russian, String english, String spanish, String type
) {

  public static WordListPresentation fromDto(WordDto dto) {
    return new WordListPresentation(
      dto.id()
        .toString(), dto.russian(), dto.english(), dto.spanish(), dto.type()
    );
  }

}
