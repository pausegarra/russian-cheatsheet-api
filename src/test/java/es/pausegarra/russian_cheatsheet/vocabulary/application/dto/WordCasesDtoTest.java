package es.pausegarra.russian_cheatsheet.vocabulary.application.dto;

import es.pausegarra.russian_cheatsheet.mother.words.requests.WordCasesRequestMother;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordCasesEntity;
import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.requests.WordCasesRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordCasesDtoTest {

  @Test
  public void shouldMapToEntity() {
    WordCasesRequest request = WordCasesRequestMother.random()
      .build();

    WordCasesDto dto = request.toDto();
    WordCasesEntity entity = dto.toEntity();

    assertNotNull(entity);
    assertEquals(request.nominativeMasculine(), entity.getNominativeMasculine());
    assertEquals(request.nominativeFeminine(), entity.getNominativeFeminine());
    assertEquals(request.nominativeNeuter(), entity.getNominativeNeuter());
    assertEquals(request.genitiveMasculine(), entity.getGenitiveMasculine());
    assertEquals(request.genitiveFeminine(), entity.getGenitiveFeminine());
    assertEquals(request.genitiveNeuter(), entity.getGenitiveNeuter());
    assertEquals(request.dativeMasculine(), entity.getDativeMasculine());
    assertEquals(request.dativeFeminine(), entity.getDativeFeminine());
    assertEquals(request.dativeNeuter(), entity.getDativeNeuter());
    assertEquals(request.accusativeMasculine(), entity.getAccusativeMasculine());
    assertEquals(request.accusativeFeminine(), entity.getAccusativeFeminine());
    assertEquals(request.accusativeNeuter(), entity.getAccusativeNeuter());
    assertEquals(request.instrumentalMasculine(), entity.getInstrumentalMasculine());
    assertEquals(request.instrumentalFeminine(), entity.getInstrumentalFeminine());
    assertEquals(request.instrumentalNeuter(), entity.getInstrumentalNeuter());
    assertEquals(request.prepositionalMasculine(), entity.getPrepositionalMasculine());
    assertEquals(request.prepositionalFeminine(), entity.getPrepositionalFeminine());
    assertEquals(request.prepositionalNeuter(), entity.getPrepositionalNeuter());
  }

}