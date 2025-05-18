package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.presentations;

import es.pausegarra.russian_cheatsheet.mother.WordCaseMother;
import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordCaseDto;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordCasesEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordCasePresentationTest {

  @Test
  public void shouldMapFromDto() {
    WordCasesEntity entity = WordCaseMother.random()
      .build();
    WordCaseDto dto = WordCaseDto.fromEntity(entity);

    WordCasePresentation presentation = WordCasePresentation.fromDto(dto);

    assertEquals(dto.nominativeMasculine(), presentation.nominativeMasculine());
    assertEquals(dto.nominativeFeminine(), presentation.nominativeFeminine());
    assertEquals(dto.nominativeNeuter(), presentation.nominativeNeuter());
    assertEquals(dto.genitiveMasculine(), presentation.genitiveMasculine());
    assertEquals(dto.genitiveFeminine(), presentation.genitiveFeminine());
    assertEquals(dto.genitiveNeuter(), presentation.genitiveNeuter());
    assertEquals(dto.dativeMasculine(), presentation.dativeMasculine());
    assertEquals(dto.dativeFeminine(), presentation.dativeFeminine());
    assertEquals(dto.dativeNeuter(), presentation.dativeNeuter());
    assertEquals(dto.accusativeMasculine(), presentation.accusativeMasculine());
    assertEquals(dto.accusativeFeminine(), presentation.accusativeFeminine());
    assertEquals(dto.accusativeNeuter(), presentation.accusativeNeuter());
    assertEquals(dto.instrumentalMasculine(), presentation.instrumentalMasculine());
    assertEquals(dto.instrumentalFeminine(), presentation.instrumentalFeminine());
    assertEquals(dto.instrumentalNeuter(), presentation.instrumentalNeuter());
    assertEquals(dto.prepositionalMasculine(), presentation.prepositionalMasculine());
    assertEquals(dto.prepositionalFeminine(), presentation.prepositionalFeminine());
    assertEquals(dto.prepositionalNeuter(), presentation.prepositionalNeuter());
  }

}