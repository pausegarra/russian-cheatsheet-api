package es.pausegarra.template.config;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.HashMap;
import java.util.Map;

public class PostgreSqlTestContainer implements QuarkusTestResourceLifecycleManager {

  private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16").withDatabaseName(
    "testdb"
  ).withUsername("test").withPassword("test");

  @Override
  public Map<String, String> start() {
    POSTGRES.start();
    Map<String, String> config = new HashMap<>();
    config.put("quarkus.datasource.jdbc.url", POSTGRES.getJdbcUrl());
    config.put("quarkus.datasource.username", POSTGRES.getUsername());
    config.put("quarkus.datasource.password", POSTGRES.getPassword());
    return config;
  }

  @Override
  public void stop() {
    POSTGRES.stop();
  }

}
