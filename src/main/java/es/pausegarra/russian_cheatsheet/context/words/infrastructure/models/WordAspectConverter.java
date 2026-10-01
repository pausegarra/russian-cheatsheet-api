package es.pausegarra.russian_cheatsheet.context.words.infrastructure.models;

import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordAspect;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class WordAspectConverter implements AttributeConverter<WordAspect, String> {

  @Override
  public String convertToDatabaseColumn(WordAspect aspect) {
    return aspect == null ? null : aspect.value();
  }

  @Override
  public WordAspect convertToEntityAttribute(String value) {
    return value == null ? null : WordAspect.fromValue(value);
  }
}
