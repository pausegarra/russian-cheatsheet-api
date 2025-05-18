package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.requests;

import es.pausegarra.russian_cheatsheet.mother.words.requests.WordCasesRequestMother;
import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordCasesDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordCasesRequestTest {

  @Test
  public void shouldMapToDto() {
    WordCasesRequest request = WordCasesRequestMother.random()
      .build();

    WordCasesDto dto = request.toDto();

    assertNotNull(dto);
    assertEquals(request.nominativeMasculine(), dto.nominativeMasculine());
    assertEquals(request.nominativeFeminine(), dto.nominativeFeminine());
    assertEquals(request.nominativeNeuter(), dto.nominativeNeuter());
    assertEquals(request.genitiveMasculine(), dto.genitiveMasculine());
    assertEquals(request.genitiveFeminine(), dto.genitiveFeminine());
    assertEquals(request.genitiveNeuter(), dto.genitiveNeuter());
    assertEquals(request.dativeMasculine(), dto.dativeMasculine());
    assertEquals(request.dativeFeminine(), dto.dativeFeminine());
    assertEquals(request.dativeNeuter(), dto.dativeNeuter());
    assertEquals(request.accusativeMasculine(), dto.accusativeMasculine());
    assertEquals(request.accusativeFeminine(), dto.accusativeFeminine());
    assertEquals(request.accusativeNeuter(), dto.accusativeNeuter());
    assertEquals(request.instrumentalMasculine(), dto.instrumentalMasculine());
    assertEquals(request.instrumentalFeminine(), dto.instrumentalFeminine());
    assertEquals(request.instrumentalNeuter(), dto.instrumentalNeuter());
    assertEquals(request.prepositionalMasculine(), dto.prepositionalMasculine());
    assertEquals(request.prepositionalFeminine(), dto.prepositionalFeminine());
    assertEquals(request.prepositionalNeuter(), dto.prepositionalNeuter());
  }

}