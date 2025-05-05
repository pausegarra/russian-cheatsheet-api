package es.pausegarra.russian_cheatsheet.auth.application.dto;

import java.util.List;

public record PermissionsDto(
  List<String> permissions
) {

  public static PermissionsDto from(List<String> permissions) {
    return new PermissionsDto(permissions);
  }

}
