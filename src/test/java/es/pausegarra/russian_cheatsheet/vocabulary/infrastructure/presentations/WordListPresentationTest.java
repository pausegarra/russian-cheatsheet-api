package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.presentations;

import es.pausegarra.russian_cheatsheet.mother.WordEntityMother;
import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordListPresentationTest {

  @Test
  public void shouldMapFromDto() {
    WordEntity word = WordEntityMother.random()
      .build();
    WordDto dto = WordDto.fromEntity(word);

    WordListPresentation presentation = WordListPresentation.fromDto(dto);

    assertEquals(dto.id().toString(), presentation.id());
    assertEquals(dto.russian(), presentation.russian());
    assertEquals(dto.english(), presentation.english());
    assertEquals(dto.spanish(), presentation.spanish());
    assertEquals(dto.type(), presentation.type());
  }

}