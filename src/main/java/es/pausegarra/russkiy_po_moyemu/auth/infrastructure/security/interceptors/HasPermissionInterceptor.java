package es.pausegarra.russkiy_po_moyemu.auth.infrastructure.security.interceptors;

import es.pausegarra.russkiy_po_moyemu.auth.infrastructure.rest_clients.KeycloakRestClient;
import es.pausegarra.russkiy_po_moyemu.auth.infrastructure.security.annotations.HasPermission;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.ForbiddenException;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

@Interceptor
@HasPermission({""})
@Priority(900)
public class HasPermissionInterceptor {

  @Inject
  @RestClient
  KeycloakRestClient keycloakRestClient;

  @Inject
  JsonWebToken jwt;

  @AroundInvoke
  public Object hasPermission(InvocationContext context) throws Exception {
    String[] requiredPermission = context.getMethod().getAnnotation(HasPermission.class).value();

    for (String permission : requiredPermission) {
      try {
        keycloakRestClient.checkPermission(
            "urn:ietf:params:oauth:grant-type:uma-ticket",
            "ruskiy-shpargalka",
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
