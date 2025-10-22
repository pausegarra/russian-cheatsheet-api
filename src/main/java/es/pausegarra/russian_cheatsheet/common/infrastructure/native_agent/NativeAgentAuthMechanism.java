package es.pausegarra.russian_cheatsheet.common.infrastructure.native_agent;

import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.IdentityProvider;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.request.AnonymousAuthenticationRequest;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@IfBuildProfile(
  "native-agent"
)
public class NativeAgentAuthMechanism implements IdentityProvider<AnonymousAuthenticationRequest> {

  @Override
  public Uni<SecurityIdentity> authenticate(
    AnonymousAuthenticationRequest tokenAuthenticationRequest,
    AuthenticationRequestContext authenticationRequestContext
  ) {
    SecurityIdentity securityIdentity = QuarkusSecurityIdentity.builder().setPrincipal(() -> "anonymous").build();

    return Uni.createFrom().item(securityIdentity);
  }

  @Override
  public Class<AnonymousAuthenticationRequest> getRequestType() {
    return AnonymousAuthenticationRequest.class;
  }

}
