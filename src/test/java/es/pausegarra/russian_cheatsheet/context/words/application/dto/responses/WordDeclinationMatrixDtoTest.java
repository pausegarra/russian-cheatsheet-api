package es.pausegarra.russian_cheatsheet.context.words.application.dto.responses;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDeclinationMatrixDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordDeclinationMatrixEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import es.pausegarra.russian_cheatsheet.mother.WordDeclinationMatrixMother;
import es.pausegarra.russian_cheatsheet.mother.WordMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WordDeclinationMatrixDtoTest {

  @Test
  public void shouldMapFromEntity() {
    WordDeclinationMatrixEntity wordDeclinationMatrixDto = WordDeclinationMatrixMother.random()
      .word(WordMother.random().type(WordType.OTHER).build())
      .build();

    WordDeclinationMatrixDto wordDeclinationMatrixDtoDto = WordDeclinationMatrixDto.fromEntity(
      wordDeclinationMatrixDto,
      WordDto.fromEntity(wordDeclinationMatrixDto.word())
    );

    assertNotNull(wordDeclinationMatrixDtoDto);
    assertEquals(wordDeclinationMatrixDto.id(), wordDeclinationMatrixDtoDto.id());
    assertEquals(wordDeclinationMatrixDto.nominativeMasculine(), wordDeclinationMatrixDtoDto.nominativeMasculine());
    assertEquals(wordDeclinationMatrixDto.nominativeFeminine(), wordDeclinationMatrixDtoDto.nominativeFeminine());
    assertEquals(wordDeclinationMatrixDto.nominativeNeuter(), wordDeclinationMatrixDtoDto.nominativeNeuter());
    assertEquals(wordDeclinationMatrixDto.nominativePlural(), wordDeclinationMatrixDtoDto.nominativePlural());
    assertEquals(wordDeclinationMatrixDto.accusativeMasculine(), wordDeclinationMatrixDtoDto.accusativeMasculine());
    assertEquals(wordDeclinationMatrixDto.accusativeFeminine(), wordDeclinationMatrixDtoDto.accusativeFeminine());
    assertEquals(wordDeclinationMatrixDto.accusativeNeuter(), wordDeclinationMatrixDtoDto.accusativeNeuter());
    assertEquals(wordDeclinationMatrixDto.accusativePlural(), wordDeclinationMatrixDtoDto.accusativePlural());
    assertEquals(wordDeclinationMatrixDto.genitiveMasculine(), wordDeclinationMatrixDtoDto.genitiveMasculine());
    assertEquals(wordDeclinationMatrixDto.genitiveFeminine(), wordDeclinationMatrixDtoDto.genitiveFeminine());
    assertEquals(wordDeclinationMatrixDto.genitiveNeuter(), wordDeclinationMatrixDtoDto.genitiveNeuter());
    assertEquals(wordDeclinationMatrixDto.genitivePlural(), wordDeclinationMatrixDtoDto.genitivePlural());
    assertEquals(wordDeclinationMatrixDto.dativeMasculine(), wordDeclinationMatrixDtoDto.dativeMasculine());
    assertEquals(wordDeclinationMatrixDto.dativeFeminine(), wordDeclinationMatrixDtoDto.dativeFeminine());
    assertEquals(wordDeclinationMatrixDto.dativeNeuter(), wordDeclinationMatrixDtoDto.dativeNeuter());
    assertEquals(wordDeclinationMatrixDto.dativePlural(), wordDeclinationMatrixDtoDto.dativePlural());
    assertEquals(wordDeclinationMatrixDto.instrumentalMasculine(), wordDeclinationMatrixDtoDto.instrumentalMasculine());
    assertEquals(wordDeclinationMatrixDto.instrumentalFeminine(), wordDeclinationMatrixDtoDto.instrumentalFeminine());
    assertEquals(wordDeclinationMatrixDto.instrumentalNeuter(), wordDeclinationMatrixDtoDto.instrumentalNeuter());
    assertEquals(wordDeclinationMatrixDto.instrumentalPlural(), wordDeclinationMatrixDtoDto.instrumentalPlural());
    assertEquals(wordDeclinationMatrixDto.prepositionalMasculine(), wordDeclinationMatrixDtoDto.prepositionalMasculine());
    assertEquals(wordDeclinationMatrixDto.prepositionalFeminine(), wordDeclinationMatrixDtoDto.prepositionalFeminine());
    assertEquals(wordDeclinationMatrixDto.prepositionalNeuter(), wordDeclinationMatrixDtoDto.prepositionalNeuter());
    assertEquals(wordDeclinationMatrixDto.prepositionalPlural(), wordDeclinationMatrixDtoDto.prepositionalPlural());
    assertEquals(wordDeclinationMatrixDto.createdBy(), wordDeclinationMatrixDtoDto.createdBy());
    assertEquals(wordDeclinationMatrixDto.createdAt(), wordDeclinationMatrixDtoDto.createdAt());
    assertEquals(wordDeclinationMatrixDto.updatedBy(), wordDeclinationMatrixDtoDto.updatedBy());
    assertEquals(wordDeclinationMatrixDto.updatedAt(), wordDeclinationMatrixDtoDto.updatedAt());
  }

}