package es.pausegarra.russkiy_po_moyemu.vocabulary.domain.exception;

import es.pausegarra.russkiy_po_moyemu.common.domain.exception.NotFound;

public final class WordNotFound extends NotFound {

  public WordNotFound(String id) {
    super("Word with ID " + id + " not found");
  }

}
