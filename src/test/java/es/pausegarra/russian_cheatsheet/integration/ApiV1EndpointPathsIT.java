package es.pausegarra.russian_cheatsheet.integration;

import es.pausegarra.russian_cheatsheet.base.IntegrationTest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;

@QuarkusTest
class ApiV1EndpointPathsIT extends IntegrationTest {

  @Test
  void shouldExposeWordEndpointsUnderV1Prefix() {
    given().when().get("http://localhost:" + RestAssured.port + "/api/v1/words")
      .then().statusCode(200);
  }

  @Test
  void shouldExposeOpenApiDocumentUnderHttpRootPath() {
    given().accept("application/json")
      .when().get("http://localhost:" + RestAssured.port + "/api/q/openapi")
      .then().statusCode(200)
      .body("paths", hasKey("/api/v1/words"))
      .body("paths", hasKey("/api/v1/words/relations"));
  }

  @Test
  void shouldExposeHealthProbeUnderHttpRootPath() {
    given().when().get("http://localhost:" + RestAssured.port + "/api/q/health/live")
      .then().statusCode(200);
  }

  @Test
  void shouldExposeHealthEndpointUnderHttpRootPath() {
    given().when().get("http://localhost:" + RestAssured.port + "/api/q/health")
      .then().statusCode(200);
  }

  @Test
  void shouldExposeReadinessProbeUnderHttpRootPath() {
    given().when().get("http://localhost:" + RestAssured.port + "/api/q/health/ready")
      .then().statusCode(200);
  }

}
