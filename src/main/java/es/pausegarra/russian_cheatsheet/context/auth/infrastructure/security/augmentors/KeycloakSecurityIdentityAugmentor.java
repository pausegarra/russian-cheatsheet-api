package es.pausegarra.russian_cheatsheet.context.auth.infrastructure.security.augmentors;

import es.pausegarra.russian_cheatsheet.context.auth.infrastructure.security.suppliers.KeycloakIdentitySupplier;
import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.SecurityIdentityAugmentor;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@Priority(1)
@RequiredArgsConstructor
public class KeycloakSecurityIdentityAugmentor implements SecurityIdentityAugmentor {

  private final Instance<KeycloakIdentitySupplier> keycloakIdentitySupplier;

  @Override
  public Uni<SecurityIdentity> augment(SecurityIdentity identity, AuthenticationRequestContext context) {
    if (identity.isAnonymous()) {
      return Uni.createFrom().item(identity);
    }

    KeycloakIdentitySupplier keycloakIdentitySupplier = this.keycloakIdentitySupplier.get();
    keycloakIdentitySupplier.setIdentity(identity);

    return context.runBlocking(keycloakIdentitySupplier);
  }


}
