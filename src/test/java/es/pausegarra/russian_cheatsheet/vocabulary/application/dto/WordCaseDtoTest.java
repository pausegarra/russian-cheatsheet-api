package es.pausegarra.russian_cheatsheet.vocabulary.application.dto;

import es.pausegarra.russian_cheatsheet.mother.WordCaseMother;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordCasesEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordCaseDtoTest {

  @Test
  public void shouldMapFromEntity() {
    WordCasesEntity entity = WordCaseMother.random()
      .build();

    WordCaseDto dto = WordCaseDto.fromEntity(entity);

    assertEquals(entity.getNominativeMasculine(), dto.nominativeMasculine());
    assertEquals(entity.getNominativeFeminine(), dto.nominativeFeminine());
    assertEquals(entity.getNominativeNeuter(), dto.nominativeNeuter());
    assertEquals(entity.getGenitiveMasculine(), dto.genitiveMasculine());
    assertEquals(entity.getGenitiveFeminine(), dto.genitiveFeminine());
    assertEquals(entity.getGenitiveNeuter(), dto.genitiveNeuter());
    assertEquals(entity.getDativeMasculine(), dto.dativeMasculine());
    assertEquals(entity.getDativeFeminine(), dto.dativeFeminine());
    assertEquals(entity.getDativeNeuter(), dto.dativeNeuter());
    assertEquals(entity.getAccusativeMasculine(), dto.accusativeMasculine());
    assertEquals(entity.getAccusativeFeminine(), dto.accusativeFeminine());
    assertEquals(entity.getAccusativeNeuter(), dto.accusativeNeuter());
    assertEquals(entity.getInstrumentalMasculine(), dto.instrumentalMasculine());
    assertEquals(entity.getInstrumentalFeminine(), dto.instrumentalFeminine());
    assertEquals(entity.getInstrumentalNeuter(), dto.instrumentalNeuter());
    assertEquals(entity.getPrepositionalMasculine(), dto.prepositionalMasculine());
    assertEquals(entity.getPrepositionalFeminine(), dto.prepositionalFeminine());
    assertEquals(entity.getPrepositionalNeuter(), dto.prepositionalNeuter());
  }

}