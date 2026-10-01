package es.pausegarra.russian_cheatsheet.context.words.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumeration = {"adjective", "adverb", "expression", "noun", "other", "pronoun", "verb"})
public enum WordType {

  ADJECTIVE("adjective"),
  ADVERB("adverb"),
  EXPRESSION("expression"),
  NOUN("noun"),
  OTHER("other"),
  PRONOUN("pronoun"),
  VERB("verb");

  private final String value;

  WordType(String value) {
    this.value = value;
  }

  @JsonValue
  public String value() {
    return value;
  }

  @JsonCreator
  public static WordType fromValue(String value) {
    return Arrays.stream(values())
      .filter(type -> type.value.equals(value))
      .findFirst()
      .orElseThrow(() -> new IllegalArgumentException("Unsupported OpenRussian word type: " + value));
  }
}
