package es.pausegarra.russian_cheatsheet.common.application.interfaces;

import jakarta.validation.Valid;

public interface Service<T, R> {

  R handle(
    @Valid
    T dto
  );

}
