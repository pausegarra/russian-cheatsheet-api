package es.pausegarra.russian_cheatsheet.context.words.domain.entities;

import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.WordCannotHaveConjugations;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.WordCannotHaveDeclinationMatrix;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.WordCannotHaveDeclinations;
import es.pausegarra.russian_cheatsheet.mother.WordConjugationMother;
import es.pausegarra.russian_cheatsheet.mother.WordDeclinationMatrixMother;
import es.pausegarra.russian_cheatsheet.mother.WordDeclinationMother;
import es.pausegarra.russian_cheatsheet.mother.WordMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordEntityTest {

  @Test
  public void shouldAddConjugationsWhenTypeIsVerb() {
    WordEntity word = WordMother.random().type(WordType.VERB).build();

    WordConjugationEntity conjugations = WordConjugationMother.random().build();
    WordEntity wordWithConjugations = word.addConjugations(conjugations);

    assertNotNull(wordWithConjugations);
    assertEquals(word.russian(), wordWithConjugations.russian());
    assertEquals(word.english(), wordWithConjugations.english());
    assertEquals(word.spanish(), wordWithConjugations.spanish());
    assertEquals(word.type(), wordWithConjugations.type());
    assertEquals(conjugations, wordWithConjugations.conjugations());
    assertNull(wordWithConjugations.declinations());
    assertNull(wordWithConjugations.declinationMatrix());
  }

  @Test
  public void shouldThrowExceptionWhenAddConjugationsWhenTypeIsNotVerb() {
    WordEntity word = WordMother.random().type(WordType.NOUN).build();

    WordConjugationEntity conjugations = WordConjugationMother.random().build();
    assertThrows(WordCannotHaveConjugations.class, () -> word.addConjugations(conjugations));
  }

  @Test
  public void shouldAddDeclinationsWhenTypeIsNoun() {
    WordEntity word = WordMother.random().type(WordType.NOUN).build();

    WordDeclinationEntity declinations = WordDeclinationMother.random().build();
    WordEntity wordWithDeclinations = word.addDeclinations(declinations);

    assertNotNull(wordWithDeclinations);
    assertEquals(word.russian(), wordWithDeclinations.russian());
    assertEquals(word.english(), wordWithDeclinations.english());
    assertEquals(word.spanish(), wordWithDeclinations.spanish());
    assertEquals(word.type(), wordWithDeclinations.type());
    assertEquals(declinations, wordWithDeclinations.declinations());
    assertNull(wordWithDeclinations.conjugations());
    assertNull(wordWithDeclinations.declinationMatrix());
  }

  @Test
  public void shouldThrowExceptionWhenAddDeclinationsWhenTypeIsNotNoun() {
    WordEntity word = WordMother.random().type(WordType.VERB).build();

    WordDeclinationEntity declinations = WordDeclinationMother.random().build();
    assertThrows(WordCannotHaveDeclinations.class, () -> word.addDeclinations(declinations));
  }

  @Test
  public void shouldAddDeclinationMatrixWhenTypeIsAdjectiveOrPronounOrParticipleOrOrdinal() {
    WordEntity word = WordMother.random().type(WordType.ADJECTIVE).build();

    WordDeclinationMatrixEntity declinationMatrix = WordDeclinationMatrixMother.random().build();
    WordEntity wordWithDeclinationMatrix = word.addDeclinationMatrix(declinationMatrix);

    assertNotNull(wordWithDeclinationMatrix);
    assertEquals(word.russian(), wordWithDeclinationMatrix.russian());
    assertEquals(word.english(), wordWithDeclinationMatrix.english());
    assertEquals(word.spanish(), wordWithDeclinationMatrix.spanish());
    assertEquals(word.type(), wordWithDeclinationMatrix.type());
    assertNull(wordWithDeclinationMatrix.conjugations());
    assertNull(wordWithDeclinationMatrix.declinations());
    assertEquals(declinationMatrix, wordWithDeclinationMatrix.declinationMatrix());
  }

  @Test
  public void shouldThrowExceptionWhenAddDeclinationMatrixWhenTypeIsNotAdjectiveOrPronounOrParticipleOrOrdinal() {
    WordEntity word = WordMother.random().type(WordType.NOUN).build();

    WordDeclinationMatrixEntity declinationMatrix = WordDeclinationMatrixMother.random().build();
    assertThrows(WordCannotHaveDeclinationMatrix.class, () -> word.addDeclinationMatrix(declinationMatrix));
  }

  @Test
  public void shouldReturnTrueWhenCanHaveConjugations() {
    WordEntity word = WordMother.random().type(WordType.VERB).build();

    assertTrue(word.canHaveConjugations());
  }

  @Test
  public void shouldReturnFalseWhenCanHaveConjugations() {
    WordEntity word = WordMother.random().type(WordType.NOUN).build();

    assertFalse(word.canHaveConjugations());
  }

  @Test
  public void shouldReturnTrueWhenCanHaveDeclinations() {
    WordEntity word = WordMother.random().type(WordType.NOUN).build();

    assertTrue(word.canHaveDeclinations());
  }

  @Test
  public void shouldReturnFalseWhenCanHaveDeclinations() {
    WordEntity word = WordMother.random().type(WordType.VERB).build();

    assertFalse(word.canHaveDeclinations());
  }

  @Test
  public void shouldReturnTrueWhenCanHaveDeclinationMatrix() {
    WordEntity word = WordMother.random().type(WordType.ADJECTIVE).build();

    assertTrue(word.canHaveDeclinationMatrix());
  }

  @Test
  public void shouldReturnFalseWhenCanHaveDeclinationMatrix() {
    WordEntity word = WordMother.random().type(WordType.NOUN).build();

    assertFalse(word.canHaveDeclinationMatrix());
  }

}