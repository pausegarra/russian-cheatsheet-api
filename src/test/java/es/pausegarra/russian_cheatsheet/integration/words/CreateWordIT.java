package es.pausegarra.russian_cheatsheet.integration.words;

import com.fasterxml.jackson.core.JsonProcessingException;
import es.pausegarra.russian_cheatsheet.base.IntegrationTest;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordTranslationInputDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word.CreateWordDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordFormsEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordAspect;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordModel;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class CreateWordIT extends IntegrationTest {

  @Test
  @TestSecurity(user = "user", roles = "words#create")
  public void shouldCreateWordWithTranslations() throws JsonProcessingException {
    CreateWordDto dto = new CreateWordDto(
      "russian", null,
      List.of(new WordTranslationInputDto("en", "english", 0), new WordTranslationInputDto("es", "spanish", 1)),
      null, null, WordType.OTHER, null, null
    );

    given().contentType("application/json")
      .body(objectMapper.writeValueAsString(dto))
      .when().post("/words")
      .then()
      .statusCode(201)
      .body("russian", equalTo("russian"))
      .body("translations[0].language", equalTo("en"))
      .body("translations[0].text", equalTo("english"))
      .body("translations[1].language", equalTo("es"))
      .body("translations[1].text", equalTo("spanish"))
      .body("type", equalTo("other"));

    WordModel saved = em.createQuery("select w from WordModel w", WordModel.class).getSingleResult();
    assertNotNull(saved);
    assertEquals(WordType.OTHER, saved.getType());
  }

  @Test
  @TestSecurity(user = "user", roles = "words#create")
  public void shouldCreateVerbWithRootAspectAndSourceNamedForms() throws JsonProcessingException {
    WordFormsEntity forms = WordFormsEntity.builder()
      .ru_verb_presfut_sg1("ввожу")
      .ru_verb_gerund_present("вводя")
      .ru_verb_participle_active_past("вводивший")
      .build();
    CreateWordDto dto = new CreateWordDto(
      "вводить", null,
      List.of(new WordTranslationInputDto("es", "introducir", 0)),
      "Usage text", "https://example.org/audio.mp3", WordType.VERB, WordAspect.IMPERFECTIVE, forms
    );

    given().contentType("application/json")
      .body(objectMapper.writeValueAsString(dto))
      .when().post("/words")
      .then()
      .statusCode(201)
      .body("type", equalTo("verb"))
      .body("aspect", equalTo("imperfective"))
      .body("forms.ru_verb_presfut_sg1", equalTo("ввожу"))
      .body("forms.ru_verb_gerund_present", equalTo("вводя"))
      .body("usage", equalTo("Usage text"))
      .body("audioUrl", equalTo("https://example.org/audio.mp3"));

    WordModel saved = em.createQuery("select w from WordModel w", WordModel.class).getSingleResult();
    assertEquals(WordAspect.IMPERFECTIVE, saved.getAspect());
    assertEquals(forms, saved.getForms());
  }

  @Test
  @TestSecurity(user = "user", roles = "words#create")
  public void shouldRejectFormsFromAnotherWordType() throws JsonProcessingException {
    CreateWordDto dto = new CreateWordDto(
      "вводить", null, List.of(), null, null, WordType.VERB, WordAspect.PERFECTIVE,
      WordFormsEntity.builder().ru_noun_sg_nom("дом").build()
    );

    given().contentType("application/json")
      .body(objectMapper.writeValueAsString(dto))
      .when().post("/words")
      .then()
      .statusCode(400)
      .body("code", equalTo("BAD_REQUEST"));
  }

  @Test
  @TestSecurity(user = "user")
  public void shouldReturn403WhenUserIsNotAuthorized() {
    given().contentType("application/json").when().post("/words").then().statusCode(403);
  }

  @Test
  public void shouldReturn401WhenUserIsNotAuthenticated() {
    given().contentType("application/json").when().post("/words").then().statusCode(401);
  }
}
