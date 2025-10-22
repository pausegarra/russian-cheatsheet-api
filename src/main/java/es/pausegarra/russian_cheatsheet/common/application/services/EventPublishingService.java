package es.pausegarra.russian_cheatsheet.common.application.services;

import jakarta.enterprise.event.Event;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public abstract class EventPublishingService<T, R, E> implements Service<T, R> {

  private Event<E> event;

  @Override
  @Transactional
  public R handle(@Valid
  T dto) {
    R result = execute(dto);

    E event = buildEvent(dto, result);
    if (isAsync()) {
      this.event.fireAsync(event);
    } else {
      this.event.fire(event);
    }

    return result;
  }

  protected abstract R execute(T dto);

  protected abstract E buildEvent(T dto, R result);

  protected boolean isAsync() {
    return false;
  }

}
