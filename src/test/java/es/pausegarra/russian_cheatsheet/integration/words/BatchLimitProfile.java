package es.pausegarra.russian_cheatsheet.integration.words;

import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.Map;

public class BatchLimitProfile implements QuarkusTestProfile {

  @Override
  public Map<String, String> getConfigOverrides() {
    return Map.of("app.batch.max-size", "2");
  }
}
