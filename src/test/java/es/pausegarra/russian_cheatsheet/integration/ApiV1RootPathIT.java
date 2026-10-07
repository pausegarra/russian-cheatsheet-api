package es.pausegarra.russian_cheatsheet.integration;

import es.pausegarra.russian_cheatsheet.base.IntegrationTest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class ApiV1RootPathIT extends IntegrationTest {

  @Test
  void shouldExposeWordEndpointsUnderV1Prefix() {
    given().when().get("http://localhost:" + RestAssured.port + "/api/v1/words")
      .then().statusCode(200);
  }

  @Test
  void shouldExposeOpenApiDocumentUnderV1Prefix() {
    given().when().get("http://localhost:" + RestAssured.port + "/api/v1/q/openapi")
      .then().statusCode(200);
  }

}
