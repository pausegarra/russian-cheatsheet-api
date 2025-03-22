package es.pausegarra.russkiy_po_moyemu.auth.infrastructure.suppliers;

import es.pausegarra.russkiy_po_moyemu.auth.infrastructure.dto.PermissionDto;
import es.pausegarra.russkiy_po_moyemu.auth.infrastructure.rest_clients.KeycloakRestClient;
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
  KeycloakRestClient keycloakRestClient;

  @Setter
  private SecurityIdentity identity;

  @Override
  public SecurityIdentity get() {
    QuarkusSecurityIdentity.Builder builder = QuarkusSecurityIdentity.builder(identity);
    String token = null;

    Object principal = identity.getPrincipal();
    if (principal instanceof JsonWebToken jwt) {
      token = jwt.getRawToken();
    }

    List<PermissionDto> permissions = keycloakRestClient.getEntitlement(
        "urn:ietf:params:oauth:grant-type:uma-ticket",
        "ruskiy-shpargalka",
        "permissions",
        "Bearer " + token
    );

    for (PermissionDto permission : permissions) {
      String rsName = permission.rsname();

      if (permission.scopes() == null) {
        continue;
      }

      for (String scope : permission.scopes()) {
        builder.addRole(rsName + ":" + scope);
      }
    }

    return builder.build();
  }

}
