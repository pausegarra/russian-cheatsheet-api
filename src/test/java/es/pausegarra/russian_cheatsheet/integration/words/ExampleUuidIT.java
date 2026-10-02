package es.pausegarra.russian_cheatsheet.integration.words;

import es.pausegarra.russian_cheatsheet.base.IntegrationTest;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.ExampleSentenceEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.ExampleTranslationEntity;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.ExampleSentenceModel;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordModel;
import es.pausegarra.russian_cheatsheet.mother.WordMother;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@QuarkusTest
public class ExampleUuidIT extends IntegrationTest {

  @Test
  public void shouldReturnApiUuidsInsteadOfExternalIds() {
    WordModel word = WordModel.fromEntity(WordMother.random().build());
    persist(word);
    ExampleSentenceEntity sentence = ExampleSentenceEntity.create(
      "пример",
      List.of(new ExampleTranslationEntity("en", "example", 0)),
      null,
      null,
      List.of(word.getId())
    );
    persist(ExampleSentenceModel.fromEntity(sentence, List.of(word)));

    given().when()
      .get("/words/" + word.getId() + "/examples")
      .then()
      .statusCode(200)
      .body("data[0].id", notNullValue())
      .body("data[0].externalId", nullValue())
      .body("data[0].linkedWordIds[0]", equalTo(word.getId().toString()));
  }
}
