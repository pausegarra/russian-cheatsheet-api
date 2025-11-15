package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.publish_word;

import java.util.UUID;

public record PublishWordDto(
  UUID id
) {

  public static PublishWordDto from(String id) {
    return new PublishWordDto(UUID.fromString(id));
  }

}
