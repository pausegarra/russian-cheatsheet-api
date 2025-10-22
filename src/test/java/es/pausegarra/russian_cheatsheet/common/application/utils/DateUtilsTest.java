package es.pausegarra.russian_cheatsheet.common.application.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtilsTest {

  @Test
  public void test_format_date() {
    // Arrange
    String date = "20230101";
    String pattern = "yyyy-MM-dd";
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);

    // Act
    LocalDate result = DateUtils.formatDate(date);

    // Assert
    String expected = "2023-01-01";
    String actual = result.format(dateTimeFormatter);

    Assertions.assertEquals(expected, actual);
  }
}
