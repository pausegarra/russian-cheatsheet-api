package es.pausegarra.template.common.infrastructure.audit;

import jakarta.enterprise.inject.spi.CDI;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AuditorProvider {

  public String getCurrentUser() {
    return CDI.current().select(Auditor.class).get().getCurrentUser();
  }

}
