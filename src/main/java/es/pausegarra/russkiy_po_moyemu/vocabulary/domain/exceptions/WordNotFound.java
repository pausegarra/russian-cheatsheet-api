package es.pausegarra.russkiy_po_moyemu.vocabulary.domain.exceptions;

import es.pausegarra.russkiy_po_moyemu.common.domain.exception.NotFound;

public class WordNotFound extends NotFound {

  public WordNotFound(String id) {
    super("Word with ID " + id + " not found");
  }

}
