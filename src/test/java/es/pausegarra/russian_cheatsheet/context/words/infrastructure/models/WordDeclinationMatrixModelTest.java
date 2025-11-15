package es.pausegarra.russian_cheatsheet.context.words.infrastructure.models;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordDeclinationMatrixEntity;
import es.pausegarra.russian_cheatsheet.mother.WordDeclinationMatrixMother;
import es.pausegarra.russian_cheatsheet.mother.WordMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordDeclinationMatrixModelTest {

  @Test
  public void shouldMapFromEntity() {
    WordDeclinationMatrixEntity wordDeclinationMatrixEntity = WordDeclinationMatrixMother.random().build();

    WordDeclinationMatrixModel wordDeclinationMatrixModel = WordDeclinationMatrixModel.fromEntity(
      wordDeclinationMatrixEntity,
      WordModel.fromEntity(wordDeclinationMatrixEntity.word())
    );

    assertNotNull(wordDeclinationMatrixModel);
    assertEquals(wordDeclinationMatrixEntity.id(), wordDeclinationMatrixModel.getId());
    assertEquals(wordDeclinationMatrixEntity.nominativeMasculine(), wordDeclinationMatrixModel.getNominativeMasculine());
    assertEquals(wordDeclinationMatrixEntity.nominativeFeminine(), wordDeclinationMatrixModel.getNominativeFeminine());
    assertEquals(wordDeclinationMatrixEntity.nominativeNeuter(), wordDeclinationMatrixModel.getNominativeNeuter());
    assertEquals(wordDeclinationMatrixEntity.nominativePlural(), wordDeclinationMatrixModel.getNominativePlural());
    assertEquals(wordDeclinationMatrixEntity.accusativeMasculine(), wordDeclinationMatrixModel.getAccusativeMasculine());
    assertEquals(wordDeclinationMatrixEntity.accusativeFeminine(), wordDeclinationMatrixModel.getAccusativeFeminine());
    assertEquals(wordDeclinationMatrixEntity.accusativeNeuter(), wordDeclinationMatrixModel.getAccusativeNeuter());
    assertEquals(wordDeclinationMatrixEntity.accusativePlural(), wordDeclinationMatrixModel.getAccusativePlural());
    assertEquals(wordDeclinationMatrixEntity.genitiveMasculine(), wordDeclinationMatrixModel.getGenitiveMasculine());
    assertEquals(wordDeclinationMatrixEntity.genitiveFeminine(), wordDeclinationMatrixModel.getGenitiveFeminine());
    assertEquals(wordDeclinationMatrixEntity.genitiveNeuter(), wordDeclinationMatrixModel.getGenitiveNeuter());
    assertEquals(wordDeclinationMatrixEntity.genitivePlural(), wordDeclinationMatrixModel.getGenitivePlural());
    assertEquals(wordDeclinationMatrixEntity.dativeMasculine(), wordDeclinationMatrixModel.getDativeMasculine());
    assertEquals(wordDeclinationMatrixEntity.dativeFeminine(), wordDeclinationMatrixModel.getDativeFeminine());
    assertEquals(wordDeclinationMatrixEntity.dativeNeuter(), wordDeclinationMatrixModel.getDativeNeuter());
    assertEquals(wordDeclinationMatrixEntity.dativePlural(), wordDeclinationMatrixModel.getDativePlural());
    assertEquals(wordDeclinationMatrixEntity.instrumentalMasculine(), wordDeclinationMatrixModel.getInstrumentalMasculine());
    assertEquals(wordDeclinationMatrixEntity.instrumentalFeminine(), wordDeclinationMatrixModel.getInstrumentalFeminine());
    assertEquals(wordDeclinationMatrixEntity.instrumentalNeuter(), wordDeclinationMatrixModel.getInstrumentalNeuter());
    assertEquals(wordDeclinationMatrixEntity.instrumentalPlural(), wordDeclinationMatrixModel.getInstrumentalPlural());
    assertEquals(wordDeclinationMatrixEntity.prepositionalMasculine(), wordDeclinationMatrixModel.getPrepositionalMasculine());
    assertEquals(wordDeclinationMatrixEntity.prepositionalFeminine(), wordDeclinationMatrixModel.getPrepositionalFeminine());
    assertEquals(wordDeclinationMatrixEntity.prepositionalNeuter(), wordDeclinationMatrixModel.getPrepositionalNeuter());
    assertEquals(wordDeclinationMatrixEntity.prepositionalPlural(), wordDeclinationMatrixModel.getPrepositionalPlural());
    assertEquals(wordDeclinationMatrixEntity.createdBy(), wordDeclinationMatrixModel.getCreatedBy());
    assertEquals(wordDeclinationMatrixEntity.createdAt(), wordDeclinationMatrixModel.getCreatedAt());
    assertEquals(wordDeclinationMatrixEntity.updatedBy(), wordDeclinationMatrixModel.getUpdatedBy());
    assertEquals(wordDeclinationMatrixEntity.updatedAt(), wordDeclinationMatrixModel.getUpdatedAt());
  }

  @Test
  public void shouldMapToEntity() {
    WordDeclinationMatrixEntity wordDeclinationMatrixEntity = WordDeclinationMatrixMother.random().build();
    WordDeclinationMatrixModel wordDeclinationMatrixModel = WordDeclinationMatrixModel.fromEntity(
      wordDeclinationMatrixEntity,
      WordModel.fromEntity(wordDeclinationMatrixEntity.word())
    );

    WordDeclinationMatrixEntity wordDeclinationMatrixEntityFromModel =
      wordDeclinationMatrixModel.toEntity(wordDeclinationMatrixEntity.word());

    assertNotNull(wordDeclinationMatrixEntityFromModel);
    assertEquals(wordDeclinationMatrixEntity.id(), wordDeclinationMatrixEntityFromModel.id());
    assertEquals(wordDeclinationMatrixEntity.nominativeMasculine(), wordDeclinationMatrixEntityFromModel.nominativeMasculine());
    assertEquals(wordDeclinationMatrixEntity.nominativeFeminine(), wordDeclinationMatrixEntityFromModel.nominativeFeminine());
    assertEquals(wordDeclinationMatrixEntity.nominativeNeuter(), wordDeclinationMatrixEntityFromModel.nominativeNeuter());
    assertEquals(wordDeclinationMatrixEntity.nominativePlural(), wordDeclinationMatrixEntityFromModel.nominativePlural());
    assertEquals(wordDeclinationMatrixEntity.accusativeMasculine(), wordDeclinationMatrixEntityFromModel.accusativeMasculine());
    assertEquals(wordDeclinationMatrixEntity.accusativeFeminine(), wordDeclinationMatrixEntityFromModel.accusativeFeminine());
    assertEquals(wordDeclinationMatrixEntity.accusativeNeuter(), wordDeclinationMatrixEntityFromModel.accusativeNeuter());
    assertEquals(wordDeclinationMatrixEntity.accusativePlural(), wordDeclinationMatrixEntityFromModel.accusativePlural());
    assertEquals(wordDeclinationMatrixEntity.genitiveMasculine(), wordDeclinationMatrixEntityFromModel.genitiveMasculine());
    assertEquals(wordDeclinationMatrixEntity.genitiveFeminine(), wordDeclinationMatrixEntityFromModel.genitiveFeminine());
    assertEquals(wordDeclinationMatrixEntity.genitiveNeuter(), wordDeclinationMatrixEntityFromModel.genitiveNeuter());
    assertEquals(wordDeclinationMatrixEntity.genitivePlural(), wordDeclinationMatrixEntityFromModel.genitivePlural());
    assertEquals(wordDeclinationMatrixEntity.dativeMasculine(), wordDeclinationMatrixEntityFromModel.dativeMasculine());
    assertEquals(wordDeclinationMatrixEntity.dativeFeminine(), wordDeclinationMatrixEntityFromModel.dativeFeminine());
    assertEquals(wordDeclinationMatrixEntity.dativeNeuter(), wordDeclinationMatrixEntityFromModel.dativeNeuter());
    assertEquals(wordDeclinationMatrixEntity.dativePlural(), wordDeclinationMatrixEntityFromModel.dativePlural());
    assertEquals(wordDeclinationMatrixEntity.instrumentalMasculine(), wordDeclinationMatrixEntityFromModel.instrumentalMasculine());
    assertEquals(wordDeclinationMatrixEntity.instrumentalFeminine(), wordDeclinationMatrixEntityFromModel.instrumentalFeminine());
    assertEquals(wordDeclinationMatrixEntity.instrumentalNeuter(), wordDeclinationMatrixEntityFromModel.instrumentalNeuter());
    assertEquals(wordDeclinationMatrixEntity.instrumentalPlural(), wordDeclinationMatrixEntityFromModel.instrumentalPlural());
    assertEquals(wordDeclinationMatrixEntity.prepositionalMasculine(), wordDeclinationMatrixEntityFromModel.prepositionalMasculine());
    assertEquals(wordDeclinationMatrixEntity.prepositionalFeminine(), wordDeclinationMatrixEntityFromModel.prepositionalFeminine());
    assertEquals(wordDeclinationMatrixEntity.prepositionalNeuter(), wordDeclinationMatrixEntityFromModel.prepositionalNeuter());
    assertEquals(wordDeclinationMatrixEntity.prepositionalPlural(), wordDeclinationMatrixEntityFromModel.prepositionalPlural());
    assertEquals(wordDeclinationMatrixEntity.createdBy(), wordDeclinationMatrixEntityFromModel.createdBy());
    assertEquals(wordDeclinationMatrixEntity.createdAt(), wordDeclinationMatrixEntityFromModel.createdAt());
    assertEquals(wordDeclinationMatrixEntity.updatedBy(), wordDeclinationMatrixEntityFromModel.updatedBy());
    assertEquals(wordDeclinationMatrixEntity.updatedAt(), wordDeclinationMatrixEntityFromModel.updatedAt());
  }

  @Test
  public void shouldReturnNullIfEntityIsNull() {
    assertNull(WordDeclinationMatrixModel.fromEntity(null, WordModel.fromEntity(WordMother.random().build())));
  }

}