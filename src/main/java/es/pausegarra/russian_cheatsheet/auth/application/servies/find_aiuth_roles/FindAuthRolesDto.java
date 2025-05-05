package es.pausegarra.russian_cheatsheet.auth.application.servies.find_aiuth_roles;

public record FindAuthRolesDto(
  String token
) {

  public static FindAuthRolesDto from(String token) {
    return new FindAuthRolesDto(token);
  }

}
