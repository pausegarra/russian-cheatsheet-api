package es.pausegarra.russian_cheatsheet.context.words.application.dto.responses;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import es.pausegarra.russian_cheatsheet.mother.WordConjugationMother;
import es.pausegarra.russian_cheatsheet.mother.WordDeclinationMatrixMother;
import es.pausegarra.russian_cheatsheet.mother.WordDeclinationMother;
import es.pausegarra.russian_cheatsheet.mother.WordMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordDtoTest {

  @Test
  public void shouldMapFromEntity() {
    WordEntity word = WordMother.random()
      .type(WordType.OTHER)
      .build();
    WordDto wordDto = WordDto.fromEntity(word);

    assertNotNull(wordDto);
    assertEquals(word.id(), wordDto.id());
    assertEquals(word.russian(), wordDto.russian());
    assertEquals(word.english(), wordDto.english());
    assertEquals(word.spanish(), wordDto.spanish());
    assertEquals(word.type(), wordDto.type());
    assertEquals(word.createdBy(), wordDto.createdBy());
    assertEquals(word.createdAt(), wordDto.createdAt());
    assertEquals(word.updatedBy(), wordDto.updatedBy());
    assertEquals(word.updatedAt(), wordDto.updatedAt());
  }

  @Test
  public void shouldMapFromEntityWithConjugationsIfVerb() {
    WordEntity word = WordMother.random()
      .type(WordType.VERB)
      .conjugations(WordConjugationMother.random().build())
      .build();
    WordDto wordDto = WordDto.fromEntity(word);

    assertNotNull(wordDto);
    assertEquals(word.id(), wordDto.id());
    assertEquals(word.russian(), wordDto.russian());
    assertEquals(word.english(), wordDto.english());
    assertEquals(word.spanish(), wordDto.spanish());
    assertEquals(word.type(), wordDto.type());
    assertEquals(word.createdBy(), wordDto.createdBy());
    assertEquals(word.createdAt(), wordDto.createdAt());
    assertEquals(word.updatedBy(), wordDto.updatedBy());
    assertEquals(word.updatedAt(), wordDto.updatedAt());

    assertNotNull(wordDto.conjugations());
  }

  @Test
  public void shouldMapFromEntityWithDeclinationsIfNoun() {
    WordEntity word = WordMother.random()
      .type(WordType.NOUN)
      .declinations(WordDeclinationMother.random().build())
      .build();
    WordDto wordDto = WordDto.fromEntity(word);

    assertNotNull(wordDto);
    assertEquals(word.id(), wordDto.id());
    assertEquals(word.russian(), wordDto.russian());
    assertEquals(word.english(), wordDto.english());
    assertEquals(word.spanish(), wordDto.spanish());
    assertEquals(word.type(), wordDto.type());
    assertEquals(word.createdBy(), wordDto.createdBy());
    assertEquals(word.createdAt(), wordDto.createdAt());
    assertEquals(word.updatedBy(), wordDto.updatedBy());
    assertEquals(word.updatedAt(), wordDto.updatedAt());

    assertNotNull(wordDto.declinations());
  }

  @Test
  public void shouldMapFromEntityWithDeclinationMatrixIfAdjectiveOrPronounOrParticipleOrOrdinal() {
    WordEntity word = WordMother.random()
      .type(WordType.ADJECTIVE)
      .declinationMatrix(WordDeclinationMatrixMother.random().build())
      .build();
    WordDto wordDto = WordDto.fromEntity(word);

    assertNotNull(wordDto);
    assertEquals(word.id(), wordDto.id());
    assertEquals(word.russian(), wordDto.russian());
    assertEquals(word.english(), wordDto.english());
    assertEquals(word.spanish(), wordDto.spanish());
    assertEquals(word.type(), wordDto.type());
    assertEquals(word.createdBy(), wordDto.createdBy());
    assertEquals(word.createdAt(), wordDto.createdAt());
    assertEquals(word.updatedBy(), wordDto.updatedBy());
    assertEquals(word.updatedAt(), wordDto.updatedAt());

    assertNotNull(wordDto.declinationMatrix());
  }

}