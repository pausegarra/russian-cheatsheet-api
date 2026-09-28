package es.pausegarra.russian_cheatsheet.context.words.infrastructure.models;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordFormsEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordAspect;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import es.pausegarra.russian_cheatsheet.mother.WordMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordModelTest {

  @Test
  void shouldMapFromEntity() {
    WordFormsEntity forms = WordFormsEntity.builder()
      .ru_verb_presfut_sg1("ввожу")
      .ru_verb_gerund_present("вводя")
      .build();
    WordEntity entity = WordMother.random()
      .type(WordType.VERB)
      .aspect(WordAspect.IMPERFECTIVE)
      .forms(forms)
      .build();

    WordModel model = WordModel.fromEntity(entity);

    assertNotNull(model);
    assertEquals(entity.id(), model.getId());
    assertEquals(entity.russian(), model.getRussian());
    assertEquals(entity.translations(), model.getTranslations().stream().map(WordTranslationModel::toEntity).toList());
    assertEquals(entity.type(), model.getType());
    assertEquals(entity.aspect(), model.getAspect());
    assertEquals(forms, model.getForms());
  }

  @Test
  void shouldMapToEntity() {
    WordFormsEntity forms = WordFormsEntity.builder()
      .ru_adj_m_nom("новый")
      .ru_adj_comparative("новее")
      .build();
    WordEntity entity = WordMother.random().type(WordType.ADJECTIVE).forms(forms).build();

    WordEntity roundTrip = WordModel.fromEntity(entity).toEntity();

    assertNotNull(roundTrip);
    assertEquals(entity.id(), roundTrip.id());
    assertEquals(entity.russian(), roundTrip.russian());
    assertEquals(entity.translations(), roundTrip.translations());
    assertEquals(entity.type(), roundTrip.type());
    assertEquals(forms, roundTrip.forms());
    assertEquals(entity.createdBy(), roundTrip.createdBy());
    assertEquals(entity.createdAt(), roundTrip.createdAt());
    assertEquals(entity.updatedBy(), roundTrip.updatedBy());
    assertEquals(entity.updatedAt(), roundTrip.updatedAt());
  }

  @Test
  void shouldPreserveNullForms() {
    WordEntity entity = WordMother.random().forms(null).build();

    assertNull(WordModel.fromEntity(entity).toEntity().forms());
  }
}
