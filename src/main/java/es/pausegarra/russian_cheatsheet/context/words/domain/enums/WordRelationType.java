package es.pausegarra.russian_cheatsheet.context.words.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumeration = {"related", "synonym", "antonym"})
public enum WordRelationType {

  RELATED("related"),
  SYNONYM("synonym"),
  ANTONYM("antonym");

  private final String value;

  WordRelationType(String value) {
    this.value = value;
  }

  @JsonValue
  public String value() {
    return value;
  }

  @JsonCreator
  public static WordRelationType fromValue(String value) {
    return Arrays.stream(values())
      .filter(relation -> relation.value.equals(value))
      .findFirst()
      .orElseThrow(() -> new IllegalArgumentException("Unsupported OpenRussian word relation: " + value));
  }
}
