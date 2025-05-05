package es.pausegarra.russian_cheatsheet.auth.infrastructure.security.suppliers;

import es.pausegarra.russian_cheatsheet.auth.infrastructure.config.KeycloakConfig;
import es.pausegarra.russian_cheatsheet.auth.domain.dto.PermissionDto;
import es.pausegarra.russian_cheatsheet.auth.domain.repositories.KeycloakRepository;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.function.Supplier;

@Dependent
@RequiredArgsConstructor
public class KeycloakIdentitySupplier implements Supplier<SecurityIdentity> {

  @RestClient
  @Inject
  KeycloakRepository keycloakRepository;

  @Setter
  private SecurityIdentity identity;

  private final KeycloakConfig keycloakConfig;

  @Override
  public SecurityIdentity get() {
    QuarkusSecurityIdentity.Builder builder = QuarkusSecurityIdentity.builder(identity);
    String token = null;

    Object principal = identity.getPrincipal();
    if (principal instanceof JsonWebToken jwt) {
      token = jwt.getRawToken();
    }

    List<PermissionDto> permissions = keycloakRepository.getEntitlement(
      "urn:ietf:params:oauth:grant-type:uma-ticket",
      keycloakConfig.clientId(), "permissions",
      "Bearer " + token
    );

    for (PermissionDto permission : permissions) {
      String rsName = permission.rsname();

      if (permission.scopes() == null) {
        continue;
      }

      for (String scope : permission.scopes()) {
        builder.addRole(rsName + "#" + scope);
      }
    }

    return builder.build();
  }

}
