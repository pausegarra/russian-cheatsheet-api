package es.pausegarra.template.auth.application.servies.find_aiuth_roles;

import es.pausegarra.template.auth.application.dto.PermissionsDto;
import es.pausegarra.template.auth.domain.dto.PermissionDto;
import es.pausegarra.template.auth.domain.repositories.KeycloakRepository;
import es.pausegarra.template.common.application.interfaces.Service;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class FindAuthRolesService implements Service<FindAuthRolesDto, PermissionsDto> {

  @RestClient
  @Inject
  KeycloakRepository keycloakRepository;

  @Override
  public PermissionsDto handle(FindAuthRolesDto dto) {
    List<PermissionDto> permissions = keycloakRepository.getEntitlement(
      "urn:ietf:params:oauth:grant-type:uma-ticket",
      dto.clientId(),
      "permissions",
      "Bearer " + dto.token()
    );

    List<String> permissionsList = new ArrayList<>();
    for (PermissionDto permission : permissions) {
      String rsName = permission.rsname();

      if (permission.scopes() == null) {
        continue;
      }

      for (String scope : permission.scopes()) {
        permissionsList.add(rsName + "#" + scope);
      }
    }

    return PermissionsDto.from(permissionsList);
  }

}
