package es.pausegarra.russian_cheatsheet.context.words.infrastructure.models;

import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class WordTypeConverter implements AttributeConverter<WordType, String> {

  @Override
  public String convertToDatabaseColumn(WordType type) {
    return type == null ? null : type.value();
  }

  @Override
  public WordType convertToEntityAttribute(String value) {
    return value == null ? null : WordType.fromValue(value);
  }
}
