package es.pausegarra.template.common.infrastructure.audit;

import io.quarkus.arc.Unremovable;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.RequestScoped;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
@Unremovable
public class Auditor {

  private final SecurityIdentity securityIdentity;

  public String getCurrentUser() {
    if (securityIdentity.isAnonymous()) {
      return "anonymous";
    }

    return securityIdentity.getPrincipal().getName();
  }

}
