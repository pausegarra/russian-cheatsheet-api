package es.pausegarra.russian_cheatsheet.mother;

import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.VerbConjugationEntity;

import java.util.UUID;

public class VerbConjugationEntityMother {

  public static VerbConjugationEntity.VerbConjugationEntityBuilder random() {
    return VerbConjugationEntity.builder()
      .id(UUID.randomUUID())
      .word(WordEntityMother.random().build())
      .imperfectivePresentFirstPersonSingular(MotherCreator.random().name().toString())
      .imperfectivePresentSecondPersonSingular(MotherCreator.random().name().toString())
      .imperfectivePresentThirdPersonSingular(MotherCreator.random().name().toString())
      .imperfectivePresentFirstPersonPlural(MotherCreator.random().name().toString())
      .imperfectivePresentSecondPersonPlural(MotherCreator.random().name().toString())
      .imperfectivePresentThirdPersonPlural(MotherCreator.random().name().toString())

      .imperfectivePastMasculine(MotherCreator.random().name().toString())
      .imperfectivePastFeminine(MotherCreator.random().name().toString())
      .imperfectivePastNeuter(MotherCreator.random().name().toString())
      .imperfectivePastPlural(MotherCreator.random().name().toString())

      .imperfectiveFutureFirstPersonSingular(MotherCreator.random().name().toString())
      .imperfectiveFutureSecondPersonSingular(MotherCreator.random().name().toString())
      .imperfectiveFutureThirdPersonSingular(MotherCreator.random().name().toString())
      .imperfectiveFutureFirstPersonPlural(MotherCreator.random().name().toString())
      .imperfectiveFutureSecondPersonPlural(MotherCreator.random().name().toString())
      .imperfectiveFutureThirdPersonPlural(MotherCreator.random().name().toString())

      .perfectivePresentFirstPersonSingular(MotherCreator.random().name().toString())
      .perfectivePresentSecondPersonSingular(MotherCreator.random().name().toString())
      .perfectivePresentThirdPersonSingular(MotherCreator.random().name().toString())
      .perfectivePresentFirstPersonPlural(MotherCreator.random().name().toString())
      .perfectivePresentSecondPersonPlural(MotherCreator.random().name().toString())
      .perfectivePresentThirdPersonPlural(MotherCreator.random().name().toString())

      .perfectivePastMasculine(MotherCreator.random().name().toString())
      .perfectivePastFeminine(MotherCreator.random().name().toString())
      .perfectivePastNeuter(MotherCreator.random().name().toString())
      .perfectivePastPlural(MotherCreator.random().name().toString())

      .perfectiveFutureFirstPersonSingular(MotherCreator.random().name().toString())
      .perfectiveFutureSecondPersonSingular(MotherCreator.random().name().toString())
      .perfectiveFutureThirdPersonSingular(MotherCreator.random().name().toString())
      .perfectiveFutureFirstPersonPlural(MotherCreator.random().name().toString())
      .perfectiveFutureSecondPersonPlural(MotherCreator.random().name().toString())
      .perfectiveFutureThirdPersonPlural(MotherCreator.random().name().toString());
  }

}
