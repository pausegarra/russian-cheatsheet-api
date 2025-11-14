package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.find_word_by_id;

import java.util.UUID;

public record FindWordByIdDto(
  UUID id
) {

  public static FindWordByIdDto from(String id) {
    return new FindWordByIdDto(UUID.fromString(id));
  }

}
