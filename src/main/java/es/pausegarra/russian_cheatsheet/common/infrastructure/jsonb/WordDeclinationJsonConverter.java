package es.pausegarra.russian_cheatsheet.common.infrastructure.jsonb;

import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordDeclinationJson;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class WordDeclinationJsonConverter extends JsonbConverter<WordDeclinationJson> {

  public WordDeclinationJsonConverter() {
    super(WordDeclinationJson.class);
  }

}
