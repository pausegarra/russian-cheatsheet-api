package es.pausegarra.russian_cheatsheet.context.words.application.dto.responses;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordConjugationDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordConjugationEntity;
import es.pausegarra.russian_cheatsheet.mother.WordConjugationMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WordConjugationDtoTest {

  @Test
  public void shouldMapFromEntity() {
    WordConjugationEntity entity = WordConjugationMother.random().build();
    WordConjugationDto wordConjugationDto = WordConjugationDto.fromEntity(entity, WordDto.fromEntity(entity.word()));

    assertNotNull(wordConjugationDto);
    assertEquals(entity.id(), wordConjugationDto.id());
    assertEquals(entity.imperfectivePresentFirstPersonSingular(), wordConjugationDto.imperfectivePresentFirstPersonSingular());
    assertEquals(entity.imperfectivePresentSecondPersonSingular(), wordConjugationDto.imperfectivePresentSecondPersonSingular());
    assertEquals(entity.imperfectivePresentThirdPersonSingular(), wordConjugationDto.imperfectivePresentThirdPersonSingular());
    assertEquals(entity.imperfectivePresentFirstPersonPlural(), wordConjugationDto.imperfectivePresentFirstPersonPlural());
    assertEquals(entity.imperfectivePresentSecondPersonPlural(), wordConjugationDto.imperfectivePresentSecondPersonPlural());
    assertEquals(entity.imperfectivePresentThirdPersonPlural(), wordConjugationDto.imperfectivePresentThirdPersonPlural());
    assertEquals(entity.imperfectivePastMasculine(), wordConjugationDto.imperfectivePastMasculine());
    assertEquals(entity.imperfectivePastFeminine(), wordConjugationDto.imperfectivePastFeminine());
    assertEquals(entity.imperfectivePastNeuter(), wordConjugationDto.imperfectivePastNeuter());
    assertEquals(entity.imperfectivePastPlural(), wordConjugationDto.imperfectivePastPlural());
    assertEquals(entity.imperfectiveFutureFirstPersonSingular(), wordConjugationDto.imperfectiveFutureFirstPersonSingular());
    assertEquals(entity.imperfectiveFutureSecondPersonSingular(), wordConjugationDto.imperfectiveFutureSecondPersonSingular());
    assertEquals(entity.imperfectiveFutureThirdPersonSingular(), wordConjugationDto.imperfectiveFutureThirdPersonSingular());
    assertEquals(entity.imperfectiveFutureFirstPersonPlural(), wordConjugationDto.imperfectiveFutureFirstPersonPlural());
    assertEquals(entity.imperfectiveFutureSecondPersonPlural(), wordConjugationDto.imperfectiveFutureSecondPersonPlural());
    assertEquals(entity.imperfectiveFutureThirdPersonPlural(), wordConjugationDto.imperfectiveFutureThirdPersonPlural());
    assertEquals(entity.perfectivePastMasculine(), wordConjugationDto.perfectivePastMasculine());
    assertEquals(entity.perfectivePastFeminine(), wordConjugationDto.perfectivePastFeminine());
    assertEquals(entity.perfectivePastNeuter(), wordConjugationDto.perfectivePastNeuter());
    assertEquals(entity.perfectivePastPlural(), wordConjugationDto.perfectivePastPlural());
    assertEquals(entity.perfectiveFutureFirstPersonSingular(), wordConjugationDto.perfectiveFutureFirstPersonSingular());
    assertEquals(entity.perfectiveFutureSecondPersonSingular(), wordConjugationDto.perfectiveFutureSecondPersonSingular());
    assertEquals(entity.perfectiveFutureThirdPersonSingular(), wordConjugationDto.perfectiveFutureThirdPersonSingular());
    assertEquals(entity.perfectiveFutureFirstPersonPlural(), wordConjugationDto.perfectiveFutureFirstPersonPlural());
    assertEquals(entity.perfectiveFutureSecondPersonPlural(), wordConjugationDto.perfectiveFutureSecondPersonPlural());
    assertEquals(entity.perfectiveFutureThirdPersonPlural(), wordConjugationDto.perfectiveFutureThirdPersonPlural());
    assertEquals(entity.createdBy(), wordConjugationDto.createdBy());
    assertEquals(entity.createdAt(), wordConjugationDto.createdAt());
    assertEquals(entity.updatedBy(), wordConjugationDto.updatedBy());
    assertEquals(entity.updatedAt(), wordConjugationDto.updatedAt());
  }

}