package es.pausegarra.russian_cheatsheet.vocabulary.application.services.find_by_id;

public record FindWordByIdDto(
  String id
) {

  public static FindWordByIdDto from(String id) {
    return new FindWordByIdDto(id);
  }

}
