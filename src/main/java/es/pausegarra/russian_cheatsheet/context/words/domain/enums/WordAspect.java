package es.pausegarra.russian_cheatsheet.context.words.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumeration = {"imperfective", "perfective", "both"})
public enum WordAspect {

  IMPERFECTIVE("imperfective"),
  PERFECTIVE("perfective"),
  BOTH("both");

  private final String value;

  WordAspect(String value) {
    this.value = value;
  }

  @JsonValue
  public String value() {
    return value;
  }

  @JsonCreator
  public static WordAspect fromValue(String value) {
    return Arrays.stream(values())
      .filter(aspect -> aspect.value.equals(value))
      .findFirst()
      .orElseThrow(() -> new IllegalArgumentException("Unsupported OpenRussian verb aspect: " + value));
  }
}
