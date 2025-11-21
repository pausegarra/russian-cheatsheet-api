package es.pausegarra.russian_cheatsheet.context.words.infrastructure.models;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordDeclinationEntity;
import es.pausegarra.russian_cheatsheet.mother.WordDeclinationMother;
import es.pausegarra.russian_cheatsheet.mother.WordMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordDeclinationModelTest {

  @Test
  public void shouldMapFromEntity() {
    WordDeclinationEntity wordDeclinationEntity = WordDeclinationMother.random().build();

    WordDeclinationModel wordDeclinationModel = WordDeclinationModel.fromEntity(
      wordDeclinationEntity,
      WordModel.fromEntity(wordDeclinationEntity.word())
    );

    assertNotNull(wordDeclinationModel);
    assertEquals(wordDeclinationEntity.id(), wordDeclinationModel.getId());
    assertEquals(wordDeclinationEntity.nominative(), wordDeclinationModel.getNominative());
    assertEquals(wordDeclinationEntity.accusative(), wordDeclinationModel.getAccusative());
    assertEquals(wordDeclinationEntity.genitive(), wordDeclinationModel.getGenitive());
    assertEquals(wordDeclinationEntity.dative(), wordDeclinationModel.getDative());
    assertEquals(wordDeclinationEntity.instrumental(), wordDeclinationModel.getInstrumental());
    assertEquals(wordDeclinationEntity.prepositional(), wordDeclinationModel.getPrepositional());
    assertEquals(wordDeclinationEntity.nominativePlural(), wordDeclinationModel.getNominativePlural());
    assertEquals(wordDeclinationEntity.accusativePlural(), wordDeclinationModel.getAccusativePlural());
    assertEquals(wordDeclinationEntity.genitivePlural(), wordDeclinationModel.getGenitivePlural());
    assertEquals(wordDeclinationEntity.dativePlural(), wordDeclinationModel.getDativePlural());
    assertEquals(wordDeclinationEntity.instrumentalPlural(), wordDeclinationModel.getInstrumentalPlural());
    assertEquals(wordDeclinationEntity.prepositionalPlural(), wordDeclinationModel.getPrepositionalPlural());
    assertEquals(wordDeclinationEntity.createdBy(), wordDeclinationModel.getCreatedBy());
    assertEquals(wordDeclinationEntity.createdAt(), wordDeclinationModel.getCreatedAt());
    assertEquals(wordDeclinationEntity.updatedBy(), wordDeclinationModel.getUpdatedBy());
    assertEquals(wordDeclinationEntity.updatedAt(), wordDeclinationModel.getUpdatedAt());
  }

  @Test
  public void shouldMapToEntity() {
    WordDeclinationEntity wordDeclinationEntity = WordDeclinationMother.random().build();
    WordDeclinationModel wordDeclinationModel = WordDeclinationModel.fromEntity(
      wordDeclinationEntity,
      WordModel.fromEntity(wordDeclinationEntity.word())
    );

    WordDeclinationEntity wordDeclinationEntityFromModel = wordDeclinationModel.toEntity(wordDeclinationEntity.word());

    assertNotNull(wordDeclinationEntityFromModel);
    assertEquals(wordDeclinationEntity.id(), wordDeclinationEntityFromModel.id());
    assertEquals(wordDeclinationEntity.nominative(), wordDeclinationEntityFromModel.nominative());
    assertEquals(wordDeclinationEntity.accusative(), wordDeclinationEntityFromModel.accusative());
    assertEquals(wordDeclinationEntity.genitive(), wordDeclinationEntityFromModel.genitive());
    assertEquals(wordDeclinationEntity.dative(), wordDeclinationEntityFromModel.dative());
    assertEquals(wordDeclinationEntity.instrumental(), wordDeclinationEntityFromModel.instrumental());
    assertEquals(wordDeclinationEntity.prepositional(), wordDeclinationEntityFromModel.prepositional());
    assertEquals(wordDeclinationEntity.createdBy(), wordDeclinationEntityFromModel.createdBy());
    assertEquals(wordDeclinationEntity.createdAt(), wordDeclinationEntityFromModel.createdAt());
    assertEquals(wordDeclinationEntity.updatedBy(), wordDeclinationEntityFromModel.updatedBy());
    assertEquals(wordDeclinationEntity.updatedAt(), wordDeclinationEntityFromModel.updatedAt());
  }

  @Test
  public void shouldReturnNullIfEntityIsNull() {
    assertNull(WordDeclinationModel.fromEntity(null, WordModel.fromEntity(WordMother.random().build())));
  }

}