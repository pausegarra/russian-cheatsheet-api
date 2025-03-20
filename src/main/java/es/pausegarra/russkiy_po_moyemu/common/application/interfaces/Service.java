package es.pausegarra.russkiy_po_moyemu.common.application.interfaces;

import jakarta.validation.Valid;

public interface Service<T, R> {

  R handle(@Valid T command);

}
