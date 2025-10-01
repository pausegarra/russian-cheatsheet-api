package es.pausegarra.template.common.infrastructure.locale;

import es.pausegarra.template.common.application.locale.LocaleContextHolder;
import jakarta.annotation.Priority;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.Locale;

@Provider
@Priority(
  1
)
public class LocaleRequestFilter implements ContainerRequestFilter {

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    Locale locale = requestContext.getAcceptableLanguages()
      .stream()
      .filter(l -> !"*".equals(l.toString()))
      .findFirst()
      .orElse(Locale.ENGLISH);

    LocaleContextHolder.setLocale(locale);
  }

}
