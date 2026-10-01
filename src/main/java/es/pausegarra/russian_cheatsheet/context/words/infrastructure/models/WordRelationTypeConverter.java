package es.pausegarra.russian_cheatsheet.context.words.infrastructure.models;

import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordRelationType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class WordRelationTypeConverter implements AttributeConverter<WordRelationType, String> {

  @Override
  public String convertToDatabaseColumn(WordRelationType relation) {
    return relation == null ? null : relation.value();
  }

  @Override
  public WordRelationType convertToEntityAttribute(String value) {
    return value == null ? null : WordRelationType.fromValue(value);
  }
}
