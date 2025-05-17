package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.presentations;

import es.pausegarra.russian_cheatsheet.mother.VerbConjugationEntityMother;
import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordConjugationsDto;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.VerbConjugationEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordConjugationsPresentationTest {

  @Test
  public void shouldMapFromDto() {
    VerbConjugationEntity entity = VerbConjugationEntityMother.random()
      .build();
    WordConjugationsDto dto = WordConjugationsDto.fromEntity(entity);

    WordConjugationsPresentation presentation = WordConjugationsPresentation.fromDto(dto);

    assertEquals(dto.imperfectivePresentFirstPersonSingular(), presentation.imperfectivePresentFirstPersonSingular());
    assertEquals(dto.imperfectivePresentSecondPersonSingular(), presentation.imperfectivePresentSecondPersonSingular());
    assertEquals(dto.imperfectivePresentThirdPersonSingular(), presentation.imperfectivePresentThirdPersonSingular());
    assertEquals(dto.imperfectivePresentFirstPersonPlural(), presentation.imperfectivePresentFirstPersonPlural());
    assertEquals(dto.imperfectivePresentSecondPersonPlural(), presentation.imperfectivePresentSecondPersonPlural());
    assertEquals(dto.imperfectivePresentThirdPersonPlural(), presentation.imperfectivePresentThirdPersonPlural());

    assertEquals(dto.imperfectivePastMasculine(), presentation.imperfectivePastMasculine());
    assertEquals(dto.imperfectivePastFeminine(), presentation.imperfectivePastFeminine());
    assertEquals(dto.imperfectivePastNeuter(), presentation.imperfectivePastNeuter());
    assertEquals(dto.imperfectivePastPlural(), presentation.imperfectivePastPlural());

    assertEquals(dto.imperfectiveFutureFirstPersonSingular(), presentation.imperfectiveFutureFirstPersonSingular());
    assertEquals(dto.imperfectiveFutureSecondPersonSingular(), presentation.imperfectiveFutureSecondPersonSingular());
    assertEquals(dto.imperfectiveFutureThirdPersonSingular(), presentation.imperfectiveFutureThirdPersonSingular());
    assertEquals(dto.imperfectiveFutureFirstPersonPlural(), presentation.imperfectiveFutureFirstPersonPlural());
    assertEquals(dto.imperfectiveFutureSecondPersonPlural(), presentation.imperfectiveFutureSecondPersonPlural());
    assertEquals(dto.imperfectiveFutureThirdPersonPlural(), presentation.imperfectiveFutureThirdPersonPlural());

    assertEquals(dto.perfectivePresentFirstPersonSingular(), presentation.perfectivePresentFirstPersonSingular());
    assertEquals(dto.perfectivePresentSecondPersonSingular(), presentation.perfectivePresentSecondPersonSingular());
    assertEquals(dto.perfectivePresentThirdPersonSingular(), presentation.perfectivePresentThirdPersonSingular());
    assertEquals(dto.perfectivePresentFirstPersonPlural(), presentation.perfectivePresentFirstPersonPlural());
    assertEquals(dto.perfectivePresentSecondPersonPlural(), presentation.perfectivePresentSecondPersonPlural());
    assertEquals(dto.perfectivePresentThirdPersonPlural(), presentation.perfectivePresentThirdPersonPlural());

    assertEquals(dto.perfectivePastMasculine(), presentation.perfectivePastMasculine());
    assertEquals(dto.perfectivePastFeminine(), presentation.perfectivePastFeminine());
    assertEquals(dto.perfectivePastNeuter(), presentation.perfectivePastNeuter());
    assertEquals(dto.perfectivePastPlural(), presentation.perfectivePastPlural());

    assertEquals(dto.perfectiveFutureFirstPersonSingular(), presentation.perfectiveFutureFirstPersonSingular());
    assertEquals(dto.perfectiveFutureSecondPersonSingular(), presentation.perfectiveFutureSecondPersonSingular());
    assertEquals(dto.perfectiveFutureThirdPersonSingular(), presentation.perfectiveFutureThirdPersonSingular());
    assertEquals(dto.perfectiveFutureFirstPersonPlural(), presentation.perfectiveFutureFirstPersonPlural());
    assertEquals(dto.perfectiveFutureSecondPersonPlural(), presentation.perfectiveFutureSecondPersonPlural());
    assertEquals(dto.perfectiveFutureThirdPersonPlural(), presentation.perfectiveFutureThirdPersonPlural());
  }

}