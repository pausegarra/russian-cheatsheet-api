package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.presentations;

import es.pausegarra.russian_cheatsheet.mother.words.entities.WordCaseMother;
import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordCasesDto;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordCasesEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordCasePresentationTest {

  @Test
  public void shouldMapFromDto() {
    WordCasesEntity entity = WordCaseMother.random()
      .build();
    WordCasesDto dto = WordCasesDto.fromEntity(entity);

    WordCasePresentation presentation = WordCasePresentation.fromDto(dto);

    assertEquals(dto.nominative(), presentation.nominative());
    assertEquals(dto.genitive(), presentation.genitive());
    assertEquals(dto.dative(), presentation.dative());
    assertEquals(dto.accusative(), presentation.accusative());
    assertEquals(dto.instrumental(), presentation.instrumental());
    assertEquals(dto.prepositional(), presentation.prepositional());
  }

}