package es.pausegarra.russian_cheatsheet.alphabet.application.dtos;

import es.pausegarra.russian_cheatsheet.alphabet.domain.entities.LetterEntity;
import es.pausegarra.russian_cheatsheet.mother.LetterEntityMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LetterDtoTest {

  @Test
  public void shouldMapFromEntity() {
    LetterEntity entity = LetterEntityMother.random()
      .build();

    LetterDto dto = LetterDto.fromEntity(entity);

    assertEquals(entity.getId(), dto.id());
    assertEquals(entity.getCyrillic(), dto.cyrillic());
    assertEquals(entity.getLatin(), dto.latin());
    assertEquals(entity.getIpa(), dto.ipa());
  }

}