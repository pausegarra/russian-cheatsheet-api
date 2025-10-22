package es.pausegarra.russian_cheatsheet.common.application.locale;

import java.util.Locale;

public class LocaleContextHolder {

  private static final ThreadLocal<Locale> localeThreadLocal = new ThreadLocal<>();

  public static void setLocale(Locale locale) {
    localeThreadLocal.set(locale);
  }

  public static Locale getLocale() {
    return localeThreadLocal.get();
  }

  public static void clear() {
    localeThreadLocal.remove();
  }

}
