package es.pausegarra.russian_cheatsheet.vocabulary.application.dto;

import es.pausegarra.russian_cheatsheet.mother.VerbConjugationEntityMother;
import es.pausegarra.russian_cheatsheet.mother.WordConjugationsDtoMother;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.VerbConjugationEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordConjugationsDtoTest {

  @Test
  public void shouldMapFromEntity() {
    VerbConjugationEntity entity = VerbConjugationEntityMother.random()
      .build();

    WordConjugationsDto dto = WordConjugationsDto.fromEntity(entity);

    assertEquals(entity.getImperfectivePresentFirstPersonSingular(), dto.imperfectivePresentFirstPersonSingular());
    assertEquals(entity.getImperfectivePresentSecondPersonSingular(), dto.imperfectivePresentSecondPersonSingular());
    assertEquals(entity.getImperfectivePresentThirdPersonSingular(), dto.imperfectivePresentThirdPersonSingular());
    assertEquals(entity.getImperfectivePresentFirstPersonPlural(), dto.imperfectivePresentFirstPersonPlural());
    assertEquals(entity.getImperfectivePresentSecondPersonPlural(), dto.imperfectivePresentSecondPersonPlural());
    assertEquals(entity.getImperfectivePresentThirdPersonPlural(), dto.imperfectivePresentThirdPersonPlural());

    assertEquals(entity.getImperfectivePastMasculine(), dto.imperfectivePastMasculine());
    assertEquals(entity.getImperfectivePastFeminine(), dto.imperfectivePastFeminine());
    assertEquals(entity.getImperfectivePastNeuter(), dto.imperfectivePastNeuter());
    assertEquals(entity.getImperfectivePastPlural(), dto.imperfectivePastPlural());

    assertEquals(entity.getImperfectiveFutureFirstPersonSingular(), dto.imperfectiveFutureFirstPersonSingular());
    assertEquals(entity.getImperfectiveFutureSecondPersonSingular(), dto.imperfectiveFutureSecondPersonSingular());
    assertEquals(entity.getImperfectiveFutureThirdPersonSingular(), dto.imperfectiveFutureThirdPersonSingular());
    assertEquals(entity.getImperfectiveFutureFirstPersonPlural(), dto.imperfectiveFutureFirstPersonPlural());
    assertEquals(entity.getImperfectiveFutureSecondPersonPlural(), dto.imperfectiveFutureSecondPersonPlural());
    assertEquals(entity.getImperfectiveFutureThirdPersonPlural(), dto.imperfectiveFutureThirdPersonPlural());

    assertEquals(entity.getPerfectivePresentFirstPersonSingular(), dto.perfectivePresentFirstPersonSingular());
    assertEquals(entity.getPerfectivePresentSecondPersonSingular(), dto.perfectivePresentSecondPersonSingular());
    assertEquals(entity.getPerfectivePresentThirdPersonSingular(), dto.perfectivePresentThirdPersonSingular());
    assertEquals(entity.getPerfectivePresentFirstPersonPlural(), dto.perfectivePresentFirstPersonPlural());
    assertEquals(entity.getPerfectivePresentSecondPersonPlural(), dto.perfectivePresentSecondPersonPlural());
    assertEquals(entity.getPerfectivePresentThirdPersonPlural(), dto.perfectivePresentThirdPersonPlural());

    assertEquals(entity.getPerfectivePastMasculine(), dto.perfectivePastMasculine());
    assertEquals(entity.getPerfectivePastFeminine(), dto.perfectivePastFeminine());
    assertEquals(entity.getPerfectivePastNeuter(), dto.perfectivePastNeuter());
    assertEquals(entity.getPerfectivePastPlural(), dto.perfectivePastPlural());

    assertEquals(entity.getPerfectiveFutureFirstPersonSingular(), dto.perfectiveFutureFirstPersonSingular());
    assertEquals(entity.getPerfectiveFutureSecondPersonSingular(), dto.perfectiveFutureSecondPersonSingular());
    assertEquals(entity.getPerfectiveFutureThirdPersonSingular(), dto.perfectiveFutureThirdPersonSingular());
    assertEquals(entity.getPerfectiveFutureFirstPersonPlural(), dto.perfectiveFutureFirstPersonPlural());
    assertEquals(entity.getPerfectiveFutureSecondPersonPlural(), dto.perfectiveFutureSecondPersonPlural());
    assertEquals(entity.getPerfectiveFutureThirdPersonPlural(), dto.perfectiveFutureThirdPersonPlural());
  }

  @Test
  public void shouldMapToEntity() {
    WordConjugationsDto dto = WordConjugationsDtoMother.random()
      .build();

    VerbConjugationEntity entity = dto.toEntity();

    assertEquals(dto.imperfectivePresentFirstPersonSingular(), entity.getImperfectivePresentFirstPersonSingular());
    assertEquals(dto.imperfectivePresentSecondPersonSingular(), entity.getImperfectivePresentSecondPersonSingular());
    assertEquals(dto.imperfectivePresentThirdPersonSingular(), entity.getImperfectivePresentThirdPersonSingular());
    assertEquals(dto.imperfectivePresentFirstPersonPlural(), entity.getImperfectivePresentFirstPersonPlural());
    assertEquals(dto.imperfectivePresentSecondPersonPlural(), entity.getImperfectivePresentSecondPersonPlural());
    assertEquals(dto.imperfectivePresentThirdPersonPlural(), entity.getImperfectivePresentThirdPersonPlural());

    assertEquals(dto.imperfectivePastMasculine(), entity.getImperfectivePastMasculine());
    assertEquals(dto.imperfectivePastFeminine(), entity.getImperfectivePastFeminine());
    assertEquals(dto.imperfectivePastNeuter(), entity.getImperfectivePastNeuter());
    assertEquals(dto.imperfectivePastPlural(), entity.getImperfectivePastPlural());

    assertEquals(dto.imperfectiveFutureFirstPersonSingular(), entity.getImperfectiveFutureFirstPersonSingular());
    assertEquals(dto.imperfectiveFutureSecondPersonSingular(), entity.getImperfectiveFutureSecondPersonSingular());
    assertEquals(dto.imperfectiveFutureThirdPersonSingular(), entity.getImperfectiveFutureThirdPersonSingular());
    assertEquals(dto.imperfectiveFutureFirstPersonPlural(), entity.getImperfectiveFutureFirstPersonPlural());
    assertEquals(dto.imperfectiveFutureSecondPersonPlural(), entity.getImperfectiveFutureSecondPersonPlural());
    assertEquals(dto.imperfectiveFutureThirdPersonPlural(), entity.getImperfectiveFutureThirdPersonPlural());

    assertEquals(dto.perfectivePresentFirstPersonSingular(), entity.getPerfectivePresentFirstPersonSingular());
    assertEquals(dto.perfectivePresentSecondPersonSingular(), entity.getPerfectivePresentSecondPersonSingular());
    assertEquals(dto.perfectivePresentThirdPersonSingular(), entity.getPerfectivePresentThirdPersonSingular());
    assertEquals(dto.perfectivePresentFirstPersonPlural(), entity.getPerfectivePresentFirstPersonPlural());
    assertEquals(dto.perfectivePresentSecondPersonPlural(), entity.getPerfectivePresentSecondPersonPlural());
    assertEquals(dto.perfectivePresentThirdPersonPlural(), entity.getPerfectivePresentThirdPersonPlural());

    assertEquals(dto.perfectivePastMasculine(), entity.getPerfectivePastMasculine());
    assertEquals(dto.perfectivePastFeminine(), entity.getPerfectivePastFeminine());
    assertEquals(dto.perfectivePastNeuter(), entity.getPerfectivePastNeuter());
    assertEquals(dto.perfectivePastPlural(), entity.getPerfectivePastPlural());

    assertEquals(dto.perfectiveFutureFirstPersonSingular(), entity.getPerfectiveFutureFirstPersonSingular());
    assertEquals(dto.perfectiveFutureSecondPersonSingular(), entity.getPerfectiveFutureSecondPersonSingular());
    assertEquals(dto.perfectiveFutureThirdPersonSingular(), entity.getPerfectiveFutureThirdPersonSingular());
    assertEquals(dto.perfectiveFutureFirstPersonPlural(), entity.getPerfectiveFutureFirstPersonPlural());
    assertEquals(dto.perfectiveFutureSecondPersonPlural(), entity.getPerfectiveFutureSecondPersonPlural());
    assertEquals(dto.perfectiveFutureThirdPersonPlural(), entity.getPerfectiveFutureThirdPersonPlural());
  }

}