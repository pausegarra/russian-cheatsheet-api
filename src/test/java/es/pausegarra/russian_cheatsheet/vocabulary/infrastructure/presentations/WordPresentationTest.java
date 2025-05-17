package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.presentations;

import es.pausegarra.russian_cheatsheet.mother.VerbConjugationEntityMother;
import es.pausegarra.russian_cheatsheet.mother.WordEntityMother;
import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.VerbConjugationEntity;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordPresentationTest {

  @Test
  public void shouldMapFromDto() {
    VerbConjugationEntity conjugation = VerbConjugationEntityMother.random()
      .build();
    WordEntity word = WordEntityMother.random()
      .conjugations(conjugation)
      .build();
    WordDto dto = WordDto.fromEntity(word);

    WordPresentation presentation = WordPresentation.fromDto(dto);

    assertEquals(
      dto.id()
        .toString(), presentation.id()
    );
    assertEquals(dto.russian(), presentation.russian());
    assertEquals(dto.english(), presentation.english());
    assertEquals(dto.spanish(), presentation.spanish());
    assertEquals(dto.type(), presentation.type());
    assertEquals(
      dto.conjugations()
        .imperfectivePresentFirstPersonSingular(),
      presentation.conjugations()
        .imperfectivePresentFirstPersonSingular()
    );
    assertEquals(
      dto.conjugations()
        .imperfectivePresentSecondPersonSingular(),
      presentation.conjugations()
        .imperfectivePresentSecondPersonSingular()
    );
    assertEquals(
      dto.conjugations()
        .imperfectivePresentThirdPersonSingular(),
      presentation.conjugations()
        .imperfectivePresentThirdPersonSingular()
    );
    assertEquals(
      dto.conjugations()
        .imperfectivePresentFirstPersonPlural(),
      presentation.conjugations()
        .imperfectivePresentFirstPersonPlural()
    );
    assertEquals(
      dto.conjugations()
        .imperfectivePresentSecondPersonPlural(),
      presentation.conjugations()
        .imperfectivePresentSecondPersonPlural()
    );
    assertEquals(
      dto.conjugations()
        .imperfectivePresentThirdPersonPlural(),
      presentation.conjugations()
        .imperfectivePresentThirdPersonPlural()
    );

    assertEquals(
      dto.conjugations()
        .imperfectivePastMasculine(),
      presentation.conjugations()
        .imperfectivePastMasculine()
    );
    assertEquals(
      dto.conjugations()
        .imperfectivePastFeminine(),
      presentation.conjugations()
        .imperfectivePastFeminine()
    );
    assertEquals(
      dto.conjugations()
        .imperfectivePastNeuter(),
      presentation.conjugations()
        .imperfectivePastNeuter()
    );
    assertEquals(
      dto.conjugations()
        .imperfectivePastPlural(),
      presentation.conjugations()
        .imperfectivePastPlural()
    );

    assertEquals(
      dto.conjugations()
        .imperfectiveFutureFirstPersonSingular(),
      presentation.conjugations()
        .imperfectiveFutureFirstPersonSingular()
    );
    assertEquals(
      dto.conjugations()
        .imperfectiveFutureSecondPersonSingular(),
      presentation.conjugations()
        .imperfectiveFutureSecondPersonSingular()
    );
    assertEquals(
      dto.conjugations()
        .imperfectiveFutureThirdPersonSingular(),
      presentation.conjugations()
        .imperfectiveFutureThirdPersonSingular()
    );
    assertEquals(
      dto.conjugations()
        .imperfectiveFutureFirstPersonPlural(),
      presentation.conjugations()
        .imperfectiveFutureFirstPersonPlural()
    );
    assertEquals(
      dto.conjugations()
        .imperfectiveFutureSecondPersonPlural(),
      presentation.conjugations()
        .imperfectiveFutureSecondPersonPlural()
    );
    assertEquals(
      dto.conjugations()
        .imperfectiveFutureThirdPersonPlural(),
      presentation.conjugations()
        .imperfectiveFutureThirdPersonPlural()
    );

    assertEquals(
      dto.conjugations()
        .perfectivePresentFirstPersonSingular(),
      presentation.conjugations()
        .perfectivePresentFirstPersonSingular()
    );
    assertEquals(
      dto.conjugations()
        .perfectivePresentSecondPersonSingular(),
      presentation.conjugations()
        .perfectivePresentSecondPersonSingular()
    );
    assertEquals(
      dto.conjugations()
        .perfectivePresentThirdPersonSingular(),
      presentation.conjugations()
        .perfectivePresentThirdPersonSingular()
    );
    assertEquals(
      dto.conjugations()
        .perfectivePresentFirstPersonPlural(),
      presentation.conjugations()
        .perfectivePresentFirstPersonPlural()
    );
    assertEquals(
      dto.conjugations()
        .perfectivePresentSecondPersonPlural(),
      presentation.conjugations()
        .perfectivePresentSecondPersonPlural()
    );
    assertEquals(
      dto.conjugations()
        .perfectivePresentThirdPersonPlural(),
      presentation.conjugations()
        .perfectivePresentThirdPersonPlural()
    );

    assertEquals(
      dto.conjugations()
        .perfectivePastMasculine(),
      presentation.conjugations()
        .perfectivePastMasculine()
    );
    assertEquals(
      dto.conjugations()
        .perfectivePastFeminine(),
      presentation.conjugations()
        .perfectivePastFeminine()
    );
    assertEquals(
      dto.conjugations()
        .perfectivePastNeuter(),
      presentation.conjugations()
        .perfectivePastNeuter()
    );
    assertEquals(
      dto.conjugations()
        .perfectivePastPlural(),
      presentation.conjugations()
        .perfectivePastPlural()
    );

    assertEquals(
      dto.conjugations()
        .perfectiveFutureFirstPersonSingular(),
      presentation.conjugations()
        .perfectiveFutureFirstPersonSingular()
    );
    assertEquals(
      dto.conjugations()
        .perfectiveFutureSecondPersonSingular(),
      presentation.conjugations()
        .perfectiveFutureSecondPersonSingular()
    );
    assertEquals(
      dto.conjugations()
        .perfectiveFutureThirdPersonSingular(),
      presentation.conjugations()
        .perfectiveFutureThirdPersonSingular()
    );
    assertEquals(
      dto.conjugations()
        .perfectiveFutureFirstPersonPlural(),
      presentation.conjugations()
        .perfectiveFutureFirstPersonPlural()
    );
    assertEquals(
      dto.conjugations()
        .perfectiveFutureSecondPersonPlural(),
      presentation.conjugations()
        .perfectiveFutureSecondPersonPlural()
    );
    assertEquals(
      dto.conjugations()
        .perfectiveFutureThirdPersonPlural(),
      presentation.conjugations()
        .perfectiveFutureThirdPersonPlural()
    );
  }

}