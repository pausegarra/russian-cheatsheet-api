package es.pausegarra.russian_cheatsheet.context.words.infrastructure.models;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.mother.WordConjugationMother;
import es.pausegarra.russian_cheatsheet.mother.WordDeclinationMatrixMother;
import es.pausegarra.russian_cheatsheet.mother.WordDeclinationMother;
import es.pausegarra.russian_cheatsheet.mother.WordMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordModelTest {

  @Test
  public void shouldMapFromEntity() {
    WordEntity wordEntity = WordMother.random()
      .conjugations(WordConjugationMother.random().build())
      .declinations(WordDeclinationMother.random().build())
      .declinationMatrix(WordDeclinationMatrixMother.random().build())
      .build();
    WordModel wordModel = WordModel.fromEntity(wordEntity);

    assertNotNull(wordModel);
    assertEquals(wordEntity.id(), wordModel.getId());
    assertEquals(wordEntity.russian(), wordModel.getRussian());
    assertEquals(wordEntity.english(), wordModel.getEnglish());
    assertEquals(wordEntity.spanish(), wordModel.getSpanish());
    assertEquals(wordEntity.type(), wordModel.getType());
    assertEquals(wordEntity.conjugations().id(), wordModel.getConjugations().getId());
    assertEquals(wordEntity.declinations().id(), wordModel.getDeclinations().getId());
    assertEquals(wordEntity.declinationMatrix().id(), wordModel.getDeclinationMatrix().getId());
    assertEquals(wordEntity.createdBy(), wordModel.getCreatedBy());
    assertEquals(wordEntity.createdAt(), wordModel.getCreatedAt());
    assertEquals(wordEntity.updatedBy(), wordModel.getUpdatedBy());
    assertEquals(wordEntity.updatedAt(), wordModel.getUpdatedAt());
  }

  @Test
  public void shouldMapToEntity() {
    WordEntity wordEntity = WordMother.random()
      .conjugations(WordConjugationMother.random().build())
      .declinations(WordDeclinationMother.random().build())
      .declinationMatrix(WordDeclinationMatrixMother.random().build())
      .build();
    WordModel wordModel = WordModel.fromEntity(wordEntity);

    WordEntity wordEntityFromModel = wordModel.toEntity();

    assertNotNull(wordEntityFromModel);
    assertEquals(wordEntity.id(), wordEntityFromModel.id());
    assertEquals(wordEntity.russian(), wordEntityFromModel.russian());
    assertEquals(wordEntity.english(), wordEntityFromModel.english());
    assertEquals(wordEntity.spanish(), wordEntityFromModel.spanish());
    assertEquals(wordEntity.type(), wordEntityFromModel.type());
    assertEquals(wordEntity.conjugations().id(), wordEntityFromModel.conjugations().id());
    assertEquals(wordEntity.declinations().id(), wordEntityFromModel.declinations().id());
    assertEquals(wordEntity.declinationMatrix().id(), wordEntityFromModel.declinationMatrix().id());
    assertEquals(wordEntity.createdBy(), wordEntityFromModel.createdBy());
    assertEquals(wordEntity.createdAt(), wordEntityFromModel.createdAt());
    assertEquals(wordEntity.updatedBy(), wordEntityFromModel.updatedBy());
    assertEquals(wordEntity.updatedAt(), wordEntityFromModel.updatedAt());
  }

  @Test
  public void shouldMapToEntityWithChildrenNull() {
    WordEntity wordEntity = WordMother.random().conjugations(null).declinations(null).declinationMatrix(null).build();
    WordModel wordModel = WordModel.fromEntity(wordEntity);

    WordEntity wordEntityFromModel = wordModel.toEntity();

    assertNotNull(wordEntityFromModel);
    assertEquals(wordEntity.id(), wordEntityFromModel.id());
    assertEquals(wordEntity.russian(), wordEntityFromModel.russian());
    assertEquals(wordEntity.english(), wordEntityFromModel.english());
    assertEquals(wordEntity.spanish(), wordEntityFromModel.spanish());
    assertEquals(wordEntity.type(), wordEntityFromModel.type());
    assertNull(wordEntityFromModel.conjugations());
    assertNull(wordEntityFromModel.declinations());
    assertNull(wordEntityFromModel.declinationMatrix());
    assertEquals(wordEntity.createdBy(), wordEntityFromModel.createdBy());
    assertEquals(wordEntity.createdAt(), wordEntityFromModel.createdAt());
    assertEquals(wordEntity.updatedBy(), wordEntityFromModel.updatedBy());
    assertEquals(wordEntity.updatedAt(), wordEntityFromModel.updatedAt());
  }

}