package es.pausegarra.russian_cheatsheet.common.application.locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public class LanguageTest {

  @Test
  public void test_get_language_returns_spanish_code() {
    // Arrange
    Locale locale = Locale.of("es");
    LocaleContextHolder.setLocale(locale);

    // Act
    String result = Language.getLanguage();

    // Assert
    Assertions.assertEquals("10", result);

    // Cleanup
    LocaleContextHolder.clear();
  }

  @Test
  public void test_get_language_returns_english_code() {
    // Arrange
    Locale locale = Locale.of("en");
    LocaleContextHolder.setLocale(locale);

    // Act
    String result = Language.getLanguage();

    // Assert
    Assertions.assertEquals("9", result);

    // Cleanup
    LocaleContextHolder.clear();
  }

}
