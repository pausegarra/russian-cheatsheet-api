package es.pausegarra.russian_cheatsheet.common.infrastructure.jsonb;

import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordConjugationJson;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class WordConjugationJsonConverter extends JsonbConverter<WordConjugationJson> {

  public WordConjugationJsonConverter() {
    super(WordConjugationJson.class);
  }

}
