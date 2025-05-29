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
    assertEquals(request.nominative(), dto.nominative());
    assertEquals(request.genitive(), dto.genitive());
    assertEquals(request.dative(), dto.dative());
    assertEquals(request.accusative(), dto.accusative());
    assertEquals(request.instrumental(), dto.instrumental());
    assertEquals(request.prepositional(), dto.prepositional());
  }

}