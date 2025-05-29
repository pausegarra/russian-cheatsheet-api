package es.pausegarra.russian_cheatsheet.vocabulary.application.dto;

import es.pausegarra.russian_cheatsheet.mother.words.entities.WordCaseMother;
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
    assertEquals(request.nominative(), entity.getNominative());
    assertEquals(request.genitive(), entity.getGenitive());
    assertEquals(request.dative(), entity.getDative());
    assertEquals(request.accusative(), entity.getAccusative());
    assertEquals(request.instrumental(), entity.getInstrumental());
    assertEquals(request.prepositional(), entity.getPrepositional());
  }

  @Test
  public void shouldMapFromEntity() {
    WordCasesEntity entity = WordCaseMother.random()
      .build();

    WordCasesDto dto = WordCasesDto.fromEntity(entity);

    assertEquals(entity.getNominative(), dto.nominative());
    assertEquals(entity.getGenitive(), dto.genitive());
    assertEquals(entity.getDative(), dto.dative());
    assertEquals(entity.getAccusative(), dto.accusative());
    assertEquals(entity.getInstrumental(), dto.instrumental());
    assertEquals(entity.getPrepositional(), dto.prepositional()); 
  }

}