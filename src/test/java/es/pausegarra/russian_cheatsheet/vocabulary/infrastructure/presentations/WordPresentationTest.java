package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.presentations;

import es.pausegarra.russian_cheatsheet.mother.words.entities.WordEntityMother;
import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.enums.WordTypes;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordPresentationTest {

  @Test
  public void shouldMapFromDtoWithoutCOnjugations() {
    WordEntity word = WordEntityMother.random()
      .type(WordTypes.NOUN)
      .build();
    WordDto dto = WordDto.fromEntity(word);

    WordPresentation presentation = WordPresentation.fromDto(dto);

    assertEquals(
      dto.id()
        .toString(), presentation.id()
    );
    assertEquals(dto.russian(), presentation.russian());
    assertEquals(dto.english(), presentation.english());
    assertEquals(dto.spanish(), presentation.spanish());
    assertEquals(dto.type(), presentation.type());
  }

}