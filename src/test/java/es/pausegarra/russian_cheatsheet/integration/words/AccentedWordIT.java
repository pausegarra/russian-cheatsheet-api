package es.pausegarra.russian_cheatsheet.integration.words;

import es.pausegarra.russian_cheatsheet.base.IntegrationTest;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@TestSecurity(user = "writer", roles = {"words#create", "words#update"})
class AccentedWordIT extends IntegrationTest {

  @Test
  void shouldPersistAndExposeExactSourceAccentOnDetailAndList() throws Exception {
    String accent = "  приве'т е\u0301 ё  ";
    String id = create(accent);
    assertStoredAccent(id, accent);
    given().basePath("").get("/api/v1/words/" + id).then().statusCode(200)
      .body("russian", equalTo("привет")).body("russianAccented", equalTo(accent));
    given().basePath("").queryParam("sortBy", "russianAccented").get("/api/v1/words").then().statusCode(200)
      .body("data[0].russianAccented", equalTo(accent));
  }

  @Test
  void shouldUpdateImportedAccentAndPreserveItAfterManualEdits() throws Exception {
    String id = create("приве'т");
    given().basePath("").contentType("application/json").body("""
      {"russian":"привет","russianAccented":"при'вет","externalId":"source-1","type":"noun","translations":[]}
      """).put("/api/v1/words/" + id).then().statusCode(200)
      .body("russianAccented", equalTo("при'вет"));
    assertStoredAccent(id, "при'вет");
    given().basePath("").contentType("application/json").body("""
      {"russian":"привет","russianAccented":"ignored","type":"noun","translations":[{"language":"es","text":"hola"}]}
      """).put("/api/v1/words/" + id).then().statusCode(200)
      .body("russianAccented", equalTo("при'вет"));
    given().basePath("").contentType("application/json").body("""
      {"russian":"привет","type":"noun"}
      """).put("/api/v1/words/" + id).then().statusCode(200)
      .body("russianAccented", equalTo("при'вет"));
    assertStoredAccent(id, "при'вет");
  }

  @Test
  void shouldRoundTripBatchCreateAndImportedUpdate() {
    String id = given().basePath("").contentType("application/json").body("""
      [{"russian":"привет","russianAccented":"приве'т","externalId":"batch-1","type":"noun","translations":[]}]
      """).post("/api/v1/words/batch").then().statusCode(201)
      .body("[0].russianAccented", equalTo("приве'т")).extract().path("[0].id");
    assertStoredAccent(id, "приве'т");
    given().basePath("").contentType("application/json").body("""
      [{"id":"%s","russian":"привет","russianAccented":"при'вет","externalId":"batch-1","type":"noun","translations":[]}]
      """.formatted(id)).put("/api/v1/words/batch").then().statusCode(200)
      .body("[0].russianAccented", equalTo("при'вет"));
    given().basePath("").contentType("application/json").body("""
      [{"id":"%s","russian":"привет","type":"noun"}]
      """.formatted(id)).put("/api/v1/words/batch").then().statusCode(200)
      .body("[0].russianAccented", equalTo("при'вет"));
    assertStoredAccent(id, "при'вет");
  }

  @Test
  void shouldAcceptLegacyManualAndImportedRequestsWithoutAccent() {
    String manual = given().basePath("").contentType("application/json").body("""
      {"russian":"слово","type":"noun"}
      """).post("/api/v1/words").then().statusCode(201)
      .body("$", hasKey("russianAccented")).body("russianAccented", nullValue()).extract().path("id");
    assertStoredAccent(manual, null);
    given().basePath("").contentType("application/json").body("""
      [{"russian":"привет","externalId":"legacy-1","type":"noun","translations":[]}]
      """).post("/api/v1/words/batch").then().statusCode(201)
      .body("[0]", hasKey("russianAccented")).body("[0].russianAccented", nullValue());
  }

  @Test
  void shouldClearAccentWhenSourceSendsBlankOrNull() throws Exception {
    String id = create("приве'т");
    for (String value : new String[] {"   ", null}) {
      var body = objectMapper.createObjectNode().put("russian", "привет").put("externalId", "source-1")
        .put("type", "noun").put("russianAccented", value);
      body.putArray("translations");
      given().basePath("").contentType("application/json").body(body.toString()).put("/api/v1/words/" + id)
        .then().statusCode(200).body("russianAccented", nullValue());
      assertStoredAccent(id, null);
    }
  }

  @Test
  void shouldApplyUnicodeWhiteSpacePolicyThroughImportedUpdates() {
    String id = create("приве'т");
    for (String accent : new String[] {"\u001c", "\u001f", "\u00a0", "\u0085", "\ufeff"}) {
      String expected = accent.equals("\u00a0") || accent.equals("\u0085") ? null : accent;
      var body = objectMapper.createObjectNode().put("russian", "привет").put("externalId", "source-1")
        .put("type", "noun").put("russianAccented", accent);
      body.putArray("translations");
      given().basePath("").contentType("application/json").body(body.toString())
        .put("/api/v1/words/" + id).then().statusCode(200).body("russianAccented", equalTo(expected));
      assertStoredAccent(id, expected);
    }
  }

  private String create(String accent) {
    var body = objectMapper.createObjectNode().put("russian", "привет").put("externalId", "source-1")
      .put("type", "noun").put("russianAccented", accent);
    body.putArray("translations");
    return given().basePath("").contentType("application/json").body(body.toString()).post("/api/v1/words")
      .then().statusCode(201).body("russianAccented", equalTo(accent)).extract().path("id");
  }

  private void assertStoredAccent(String id, String expected) {
    assertEquals(expected, em.createNativeQuery("select russian_accented from words where id = :id")
      .setParameter("id", UUID.fromString(id)).getSingleResult());
  }
}
