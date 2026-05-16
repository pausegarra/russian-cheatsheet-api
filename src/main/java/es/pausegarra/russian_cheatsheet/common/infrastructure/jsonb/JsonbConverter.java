package es.pausegarra.russian_cheatsheet.common.infrastructure.jsonb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.pausegarra.russian_cheatsheet.common.domain.exception.InternalServerError;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class JsonbConverter<T> implements AttributeConverter<T, String> {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  private final Class<T> clazz;

  public JsonbConverter(Class<T> clazz) {
    this.clazz = clazz;
  }

  @Override
  public String convertToDatabaseColumn(T attribute) {
    if (attribute == null) {
      return null;
    }
    try {
      return MAPPER.writeValueAsString(attribute);
    } catch (JsonProcessingException e) {
      throw new InternalServerError("Failed to serialize JSON", e);
    }
  }

  @Override
  public T convertToEntityAttribute(String dbData) {
    if (dbData == null || dbData.isBlank()) {
      return null;
    }
    try {
      return MAPPER.readValue(dbData, clazz);
    } catch (JsonProcessingException e) {
      throw new InternalServerError("Failed to deserialize JSON", e);
    }
  }

}
