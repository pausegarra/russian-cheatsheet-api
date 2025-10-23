package es.pausegarra.russian_cheatsheet.context.words.infrastructure.models;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordConjugationEntity;
import es.pausegarra.russian_cheatsheet.mother.WordConjugationMother;
import es.pausegarra.russian_cheatsheet.mother.WordMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordConjugationModelTest {

  @Test
  public void shouldMapFromEntity() {
    WordConjugationEntity wordConjugationEntity = WordConjugationMother.random().build();

    WordConjugationModel wordConjugationModel = WordConjugationModel.fromEntity(wordConjugationEntity, WordModel.fromEntity(wordConjugationEntity.word()));

    assertNotNull(wordConjugationModel);
    assertEquals(wordConjugationEntity.id(), wordConjugationModel.getId());
    assertEquals(wordConjugationEntity.imperfectivePresentFirstPersonSingular(), wordConjugationModel.getImperfectivePresentFirstPersonSingular());
    assertEquals(wordConjugationEntity.imperfectivePresentSecondPersonSingular(), wordConjugationModel.getImperfectivePresentSecondPersonSingular());
    assertEquals(wordConjugationEntity.imperfectivePresentThirdPersonSingular(), wordConjugationModel.getImperfectivePresentThirdPersonSingular());
    assertEquals(wordConjugationEntity.imperfectivePresentFirstPersonPlural(), wordConjugationModel.getImperfectivePresentFirstPersonPlural());
    assertEquals(wordConjugationEntity.imperfectivePresentSecondPersonPlural(), wordConjugationModel.getImperfectivePresentSecondPersonPlural());
    assertEquals(wordConjugationEntity.imperfectivePresentThirdPersonPlural(), wordConjugationModel.getImperfectivePresentThirdPersonPlural());
    assertEquals(wordConjugationEntity.imperfectivePastMasculine(), wordConjugationModel.getImperfectivePastMasculine());
    assertEquals(wordConjugationEntity.imperfectivePastFeminine(), wordConjugationModel.getImperfectivePastFeminine());
    assertEquals(wordConjugationEntity.imperfectivePastNeuter(), wordConjugationModel.getImperfectivePastNeuter());
    assertEquals(wordConjugationEntity.imperfectivePastPlural(), wordConjugationModel.getImperfectivePastPlural());
    assertEquals(wordConjugationEntity.imperfectiveFutureFirstPersonSingular(), wordConjugationModel.getImperfectiveFutureFirstPersonSingular());
    assertEquals(wordConjugationEntity.imperfectiveFutureSecondPersonSingular(), wordConjugationModel.getImperfectiveFutureSecondPersonSingular());
    assertEquals(wordConjugationEntity.imperfectiveFutureThirdPersonSingular(), wordConjugationModel.getImperfectiveFutureThirdPersonSingular());
    assertEquals(wordConjugationEntity.imperfectiveFutureFirstPersonPlural(), wordConjugationModel.getImperfectiveFutureFirstPersonPlural());
    assertEquals(wordConjugationEntity.imperfectiveFutureSecondPersonPlural(), wordConjugationModel.getImperfectiveFutureSecondPersonPlural());
    assertEquals(wordConjugationEntity.imperfectiveFutureThirdPersonPlural(), wordConjugationModel.getImperfectiveFutureThirdPersonPlural());
    assertEquals(wordConjugationEntity.perfectivePastMasculine(), wordConjugationModel.getPerfectivePastMasculine());
    assertEquals(wordConjugationEntity.perfectivePastFeminine(), wordConjugationModel.getPerfectivePastFeminine());
    assertEquals(wordConjugationEntity.perfectivePastNeuter(), wordConjugationModel.getPerfectivePastNeuter());
    assertEquals(wordConjugationEntity.perfectivePastPlural(), wordConjugationModel.getPerfectivePastPlural());
    assertEquals(wordConjugationEntity.perfectiveFutureFirstPersonSingular(), wordConjugationModel.getPerfectiveFutureFirstPersonSingular());
    assertEquals(wordConjugationEntity.perfectiveFutureSecondPersonSingular(), wordConjugationModel.getPerfectiveFutureSecondPersonSingular());
    assertEquals(wordConjugationEntity.perfectiveFutureThirdPersonSingular(), wordConjugationModel.getPerfectiveFutureThirdPersonSingular());
    assertEquals(wordConjugationEntity.perfectiveFutureFirstPersonPlural(), wordConjugationModel.getPerfectiveFutureFirstPersonPlural());
    assertEquals(wordConjugationEntity.perfectiveFutureSecondPersonPlural(), wordConjugationModel.getPerfectiveFutureSecondPersonPlural());
    assertEquals(wordConjugationEntity.perfectiveFutureThirdPersonPlural(), wordConjugationModel.getPerfectiveFutureThirdPersonPlural());
    assertEquals(wordConjugationEntity.createdBy(), wordConjugationModel.getCreatedBy());
    assertEquals(wordConjugationEntity.createdAt(), wordConjugationModel.getCreatedAt());
    assertEquals(wordConjugationEntity.updatedBy(), wordConjugationModel.getUpdatedBy());
    assertEquals(wordConjugationEntity.updatedAt(), wordConjugationModel.getUpdatedAt());
  }

  @Test
  public void shouldMapToEntity() {
    WordConjugationEntity wordConjugationEntity = WordConjugationMother.random().build();
    WordConjugationModel wordConjugationModel = WordConjugationModel.fromEntity(wordConjugationEntity, WordModel.fromEntity(wordConjugationEntity.word()));

    WordConjugationEntity wordConjugationEntityFromModel = wordConjugationModel.toEntity(wordConjugationEntity.word());

    assertNotNull(wordConjugationEntityFromModel);
    assertEquals(wordConjugationEntity.id(), wordConjugationEntityFromModel.id());
    assertEquals(wordConjugationEntity.imperfectivePresentFirstPersonSingular(), wordConjugationEntityFromModel.imperfectivePresentFirstPersonSingular());
    assertEquals(wordConjugationEntity.imperfectivePresentSecondPersonSingular(), wordConjugationEntityFromModel.imperfectivePresentSecondPersonSingular());
    assertEquals(wordConjugationEntity.imperfectivePresentThirdPersonSingular(), wordConjugationEntityFromModel.imperfectivePresentThirdPersonSingular());
    assertEquals(wordConjugationEntity.imperfectivePresentFirstPersonPlural(), wordConjugationEntityFromModel.imperfectivePresentFirstPersonPlural());
    assertEquals(wordConjugationEntity.imperfectivePresentSecondPersonPlural(), wordConjugationEntityFromModel.imperfectivePresentSecondPersonPlural());
    assertEquals(wordConjugationEntity.imperfectivePresentThirdPersonPlural(), wordConjugationEntityFromModel.imperfectivePresentThirdPersonPlural());
    assertEquals(wordConjugationEntity.imperfectivePastMasculine(), wordConjugationEntityFromModel.imperfectivePastMasculine());
    assertEquals(wordConjugationEntity.imperfectivePastFeminine(), wordConjugationEntityFromModel.imperfectivePastFeminine());
    assertEquals(wordConjugationEntity.imperfectivePastNeuter(), wordConjugationEntityFromModel.imperfectivePastNeuter());
    assertEquals(wordConjugationEntity.imperfectivePastPlural(), wordConjugationEntityFromModel.imperfectivePastPlural());
    assertEquals(wordConjugationEntity.imperfectiveFutureFirstPersonSingular(), wordConjugationEntityFromModel.imperfectiveFutureFirstPersonSingular());
    assertEquals(wordConjugationEntity.imperfectiveFutureSecondPersonSingular(), wordConjugationEntityFromModel.imperfectiveFutureSecondPersonSingular());
    assertEquals(wordConjugationEntity.imperfectiveFutureThirdPersonSingular(), wordConjugationEntityFromModel.imperfectiveFutureThirdPersonSingular());
    assertEquals(wordConjugationEntity.imperfectiveFutureFirstPersonPlural(), wordConjugationEntityFromModel.imperfectiveFutureFirstPersonPlural());
    assertEquals(wordConjugationEntity.imperfectiveFutureSecondPersonPlural(), wordConjugationEntityFromModel.imperfectiveFutureSecondPersonPlural());
    assertEquals(wordConjugationEntity.imperfectiveFutureThirdPersonPlural(), wordConjugationEntityFromModel.imperfectiveFutureThirdPersonPlural());
    assertEquals(wordConjugationEntity.perfectivePastMasculine(), wordConjugationEntityFromModel.perfectivePastMasculine());
    assertEquals(wordConjugationEntity.perfectivePastFeminine(), wordConjugationEntityFromModel.perfectivePastFeminine());
    assertEquals(wordConjugationEntity.perfectivePastNeuter(), wordConjugationEntityFromModel.perfectivePastNeuter());
    assertEquals(wordConjugationEntity.perfectivePastPlural(), wordConjugationEntityFromModel.perfectivePastPlural());
    assertEquals(wordConjugationEntity.perfectiveFutureFirstPersonSingular(), wordConjugationEntityFromModel.perfectiveFutureFirstPersonSingular());
    assertEquals(wordConjugationEntity.perfectiveFutureSecondPersonSingular(), wordConjugationEntityFromModel.perfectiveFutureSecondPersonSingular());
    assertEquals(wordConjugationEntity.perfectiveFutureThirdPersonSingular(), wordConjugationEntityFromModel.perfectiveFutureThirdPersonSingular());
    assertEquals(wordConjugationEntity.perfectiveFutureFirstPersonPlural(), wordConjugationEntityFromModel.perfectiveFutureFirstPersonPlural());
    assertEquals(wordConjugationEntity.perfectiveFutureSecondPersonPlural(), wordConjugationEntityFromModel.perfectiveFutureSecondPersonPlural());
    assertEquals(wordConjugationEntity.perfectiveFutureThirdPersonPlural(), wordConjugationEntityFromModel.perfectiveFutureThirdPersonPlural());
    assertEquals(wordConjugationEntity.createdBy(), wordConjugationEntityFromModel.createdBy());
    assertEquals(wordConjugationEntity.createdAt(), wordConjugationEntityFromModel.createdAt());
    assertEquals(wordConjugationEntity.updatedBy(), wordConjugationEntityFromModel.updatedBy());
    assertEquals(wordConjugationEntity.updatedAt(), wordConjugationEntityFromModel.updatedAt());
  }

  @Test
  public void shouldReturnNullIfEntityIsNull() {
    assertNull(WordConjugationModel.fromEntity(null, WordModel.fromEntity(WordMother.random().build())));
  }

}