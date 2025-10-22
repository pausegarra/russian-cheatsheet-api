package es.pausegarra.russian_cheatsheet.common.application.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

  private final static String format = "yyyyMMdd";

  private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateUtils.format);

  public static LocalDate formatDate(String date) {
    if (date == null) {
      return null;
    }

    return LocalDate.parse(date, DateUtils.dateTimeFormatter);
  }

}
