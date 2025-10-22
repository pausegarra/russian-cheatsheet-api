package es.pausegarra.russian_cheatsheet.common.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UuidUtils {

  public boolean isUuid(String s) {
    try {
      java.util.UUID.fromString(s);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

}
