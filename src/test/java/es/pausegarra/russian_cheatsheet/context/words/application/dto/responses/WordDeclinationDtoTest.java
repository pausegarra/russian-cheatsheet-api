package es.pausegarra.russian_cheatsheet.context.words.application.dto.responses;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDeclinationDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordDeclinationEntity;
import es.pausegarra.russian_cheatsheet.mother.WordDeclinationMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WordDeclinationDtoTest {

  @Test
  public void shouldMapFromEntity() {
    WordDeclinationEntity entity = WordDeclinationMother.random().build();

    WordDeclinationDto wordDeclinationDto = WordDeclinationDto.fromEntity(entity, WordDto.fromEntity(entity.word()));

    assertNotNull(wordDeclinationDto);
    assertEquals(entity.id(), wordDeclinationDto.id());
    assertEquals(entity.nominative(), wordDeclinationDto.nominative());
    assertEquals(entity.accusative(), wordDeclinationDto.accusative());
    assertEquals(entity.genitive(), wordDeclinationDto.genitive());
    assertEquals(entity.dative(), wordDeclinationDto.dative());
    assertEquals(entity.instrumental(), wordDeclinationDto.instrumental());
    assertEquals(entity.prepositional(), wordDeclinationDto.prepositional());
    assertEquals(entity.createdBy(), wordDeclinationDto.createdBy());
    assertEquals(entity.createdAt(), wordDeclinationDto.createdAt());
    assertEquals(entity.updatedBy(), wordDeclinationDto.updatedBy());
    assertEquals(entity.updatedAt(), wordDeclinationDto.updatedAt());
  }

}