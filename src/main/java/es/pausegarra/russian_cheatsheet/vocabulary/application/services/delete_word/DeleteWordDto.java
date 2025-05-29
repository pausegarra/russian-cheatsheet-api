package es.pausegarra.russian_cheatsheet.vocabulary.application.services.delete_word;

import java.util.UUID;

public record DeleteWordDto(
  UUID id
) {

  public static DeleteWordDto from(String id) {
    return new DeleteWordDto(UUID.fromString(id));
  }

}
