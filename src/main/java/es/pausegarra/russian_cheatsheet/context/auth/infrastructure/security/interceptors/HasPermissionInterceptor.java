package es.pausegarra.russian_cheatsheet.context.auth.infrastructure.security.interceptors;

import es.pausegarra.russian_cheatsheet.context.auth.domain.repositories.KeycloakRepository;
import es.pausegarra.russian_cheatsheet.context.auth.infrastructure.config.KeycloakConfig;
import es.pausegarra.russian_cheatsheet.context.auth.infrastructure.security.annotations.HasPermission;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

@Interceptor
@HasPermission({""})
@Priority(900)
@RequiredArgsConstructor
public class HasPermissionInterceptor {

  @Inject
  @RestClient
  KeycloakRepository keycloakRepository;

  @Inject
  JsonWebToken jwt;

  private final KeycloakConfig keycloakConfig;

  @AroundInvoke
  public Object hasPermission(InvocationContext context) throws Exception {
    String[] requiredPermission = context.getMethod().getAnnotation(HasPermission.class).value();

    for (String permission : requiredPermission) {
      try {
        keycloakRepository.checkPermission(
          "urn:ietf:params:oauth:grant-type:uma-ticket",
          keycloakConfig.clientId(),
          "decision",
          permission,
          "Bearer " + jwt.getRawToken()
        );
      } catch (ClientWebApplicationException e) {
        throw new ForbiddenException();
      }
    }

    return context.proceed();
  }

}
