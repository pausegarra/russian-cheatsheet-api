package es.pausegarra.russian_cheatsheet.context.words.domain.enums;

import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;

public enum WordSearchMode {
  LEGACY,
  PRECISE,
  CONTAINS;

  public static WordSearchMode fromQueryParameter(String value) {
    if (value == null) {
      return LEGACY;
    }

    return switch (value) {
      case "precise" -> PRECISE;
      case "contains" -> CONTAINS;
      default -> throw new BadRequest("Unsupported word match mode: " + value);
    };
  }
}
