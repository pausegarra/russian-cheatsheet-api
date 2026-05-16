package es.pausegarra.russian_cheatsheet.context.words.application.dto.responses;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDeclinationMatrixDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordDeclinationMatrixEntity;
import es.pausegarra.russian_cheatsheet.mother.WordDeclinationMatrixMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WordDeclinationMatrixDtoTest {

  @Test
  public void shouldMapFromEntity() {
    WordDeclinationMatrixEntity entity = WordDeclinationMatrixMother.random().build();

    WordDeclinationMatrixDto dto = WordDeclinationMatrixDto.fromEntity(entity);

    assertNotNull(dto);
    assertEquals(entity.nominativeMasculine(), dto.nominativeMasculine());
    assertEquals(entity.nominativeFeminine(), dto.nominativeFeminine());
    assertEquals(entity.nominativeNeuter(), dto.nominativeNeuter());
    assertEquals(entity.nominativePlural(), dto.nominativePlural());
    assertEquals(entity.accusativeMasculine(), dto.accusativeMasculine());
    assertEquals(entity.accusativeFeminine(), dto.accusativeFeminine());
    assertEquals(entity.accusativeNeuter(), dto.accusativeNeuter());
    assertEquals(entity.accusativePlural(), dto.accusativePlural());
    assertEquals(entity.genitiveMasculine(), dto.genitiveMasculine());
    assertEquals(entity.genitiveFeminine(), dto.genitiveFeminine());
    assertEquals(entity.genitiveNeuter(), dto.genitiveNeuter());
    assertEquals(entity.genitivePlural(), dto.genitivePlural());
    assertEquals(entity.dativeMasculine(), dto.dativeMasculine());
    assertEquals(entity.dativeFeminine(), dto.dativeFeminine());
    assertEquals(entity.dativeNeuter(), dto.dativeNeuter());
    assertEquals(entity.dativePlural(), dto.dativePlural());
    assertEquals(entity.instrumentalMasculine(), dto.instrumentalMasculine());
    assertEquals(entity.instrumentalFeminine(), dto.instrumentalFeminine());
    assertEquals(entity.instrumentalNeuter(), dto.instrumentalNeuter());
    assertEquals(entity.instrumentalPlural(), dto.instrumentalPlural());
    assertEquals(entity.prepositionalMasculine(), dto.prepositionalMasculine());
    assertEquals(entity.prepositionalFeminine(), dto.prepositionalFeminine());
    assertEquals(entity.prepositionalNeuter(), dto.prepositionalNeuter());
    assertEquals(entity.prepositionalPlural(), dto.prepositionalPlural());
  }

}
