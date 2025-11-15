package es.pausegarra.russian_cheatsheet.common.application.use_cases;

import jakarta.validation.Valid;

public interface UseCase<T, R> {

  R handle(
    @Valid
    T dto
  );

}
