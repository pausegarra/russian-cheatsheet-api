package es.pausegarra.russian_cheatsheet.vocabulary.application.dto;

import es.pausegarra.russian_cheatsheet.mother.WordEntityMother;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordDtoTest {

  @Test
  public void shouldMapFromEntity() {
    WordEntity wordEntity = WordEntityMother.random()
      .build();

    WordDto wordDto = WordDto.fromEntity(wordEntity);

    assertEquals(wordEntity.getId(), wordDto.id());
    assertEquals(wordEntity.getRussian(), wordDto.russian());
    assertEquals(wordEntity.getEnglish(), wordDto.english());
    assertEquals(wordEntity.getSpanish(), wordDto.spanish());
    assertEquals(
      wordEntity.getType()
        .toString(), wordDto.type()
    );
  }

}