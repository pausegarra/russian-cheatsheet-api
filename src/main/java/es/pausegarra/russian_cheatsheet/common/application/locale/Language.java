package es.pausegarra.russian_cheatsheet.common.application.locale;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@UtilityClass
public class Language {

  private final Map<String, String> LANGUAGES = new HashMap<>();

  public String getLanguage() {
    return LANGUAGES.get(LocaleContextHolder.getLocale().getLanguage());
  }

  public String getLanguageByCode(String code) {
    Optional<String> key = LANGUAGES.entrySet().stream().filter(entry -> entry.getValue().equals(code)).map(Map.Entry::getKey).findFirst();

    return key.orElseGet(() -> LocaleContextHolder.getLocale().getLanguage());
  }

  static {
    LANGUAGES.put("es", "10");
    LANGUAGES.put("en", "9");
  }

}
