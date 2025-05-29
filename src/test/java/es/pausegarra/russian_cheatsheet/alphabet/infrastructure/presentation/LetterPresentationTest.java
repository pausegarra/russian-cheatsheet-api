package es.pausegarra.russian_cheatsheet.alphabet.infrastructure.presentation;

import es.pausegarra.russian_cheatsheet.alphabet.application.dtos.LetterDto;
import es.pausegarra.russian_cheatsheet.alphabet.domain.entities.LetterEntity;
import es.pausegarra.russian_cheatsheet.mother.words.entities.LetterEntityMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LetterPresentationTest {

  @Test
  public void shouldMapFromDto() {
    LetterEntity entity = LetterEntityMother.random()
      .build();
    LetterDto dto = LetterDto.fromEntity(entity);

    LetterPresentation presentation = LetterPresentation.fromDto(dto);

    assertEquals(dto.id(), presentation.id());
    assertEquals(dto.cyrillic(), presentation.cyrillic());
    assertEquals(dto.latin(), presentation.latin());
    assertEquals(dto.ipa(), presentation.ipa());
  }

}