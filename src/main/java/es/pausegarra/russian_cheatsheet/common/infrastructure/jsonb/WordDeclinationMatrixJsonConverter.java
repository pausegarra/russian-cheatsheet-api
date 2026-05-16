package es.pausegarra.russian_cheatsheet.common.infrastructure.jsonb;

import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordDeclinationMatrixJson;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class WordDeclinationMatrixJsonConverter extends JsonbConverter<WordDeclinationMatrixJson> {

  public WordDeclinationMatrixJsonConverter() {
    super(WordDeclinationMatrixJson.class);
  }

}
