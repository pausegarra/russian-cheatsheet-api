package es.pausegarra.russkiy_po_moyemu.alphabet.application.services.list_letters;

public record ListLettersQuery() {

  public static ListLettersQuery from() {
    return new ListLettersQuery();
  }

}
