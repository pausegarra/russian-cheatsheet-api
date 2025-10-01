package es.pausegarra.template.context.auth.infrastructure.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;
import jakarta.inject.Singleton;

@ConfigMapping(prefix = "app.idp")
@Singleton
public interface KeycloakConfig {

  @WithName("url")
  String url();

  @WithName("client-id")
  String clientId();

}
