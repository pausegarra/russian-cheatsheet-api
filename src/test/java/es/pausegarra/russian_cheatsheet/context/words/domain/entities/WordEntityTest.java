package es.pausegarra.russian_cheatsheet.context.words.domain.entities;

import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import es.pausegarra.russian_cheatsheet.mother.WordMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordEntityTest {

  @Test
  void formsMustMatchTheWordType() {
    WordFormsEntity nounForms = WordFormsEntity.builder().ru_noun_sg_nom("дом").build();
    WordFormsEntity verbForms = WordFormsEntity.builder().ru_verb_presfut_sg1("иду").build();
    WordFormsEntity adjectiveForms = WordFormsEntity.builder().ru_adj_m_nom("новый").build();

    assertTrue(nounForms.isCompatibleWith(WordType.NOUN));
    assertFalse(nounForms.isCompatibleWith(WordType.VERB));
    assertTrue(verbForms.isCompatibleWith(WordType.VERB));
    assertFalse(verbForms.isCompatibleWith(WordType.NOUN));
    assertTrue(adjectiveForms.isCompatibleWith(WordType.PRONOUN));
    assertFalse(adjectiveForms.isCompatibleWith(WordType.OTHER));
  }

  @Test
  void baseFormCanBeUsedWithAnyType() {
    WordFormsEntity forms = WordFormsEntity.builder().ru_base("быстро").build();

    assertTrue(forms.isCompatibleWith(WordType.ADVERB));
    assertTrue(forms.isCompatibleWith(WordType.NOUN));
    assertTrue(forms.isCompatibleWith(WordType.VERB));
  }

  @Test
  void shouldPublishWord() {
    WordEntity word = WordMother.random().publishedAt(null).build();

    assertNotNull(word.publish().publishedAt());
  }
}
