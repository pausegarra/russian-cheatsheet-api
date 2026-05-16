package es.pausegarra.russian_cheatsheet.mother;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordConjugationEntity;

public class WordConjugationMother {

  public static WordConjugationEntity.WordConjugationEntityBuilder random() {
    return WordConjugationEntity.builder()
      .imperfectivePresentFirstPersonSingular(MotherCreator.random().animal().name())
      .imperfectivePresentSecondPersonSingular(MotherCreator.random().animal().name())
      .imperfectivePresentThirdPersonSingular(MotherCreator.random().animal().name())
      .imperfectivePresentFirstPersonPlural(MotherCreator.random().animal().name())
      .imperfectivePresentSecondPersonPlural(MotherCreator.random().animal().name())
      .imperfectivePresentThirdPersonPlural(MotherCreator.random().animal().name())
      .imperfectivePastMasculine(MotherCreator.random().animal().name())
      .imperfectivePastFeminine(MotherCreator.random().animal().name())
      .imperfectivePastNeuter(MotherCreator.random().animal().name())
      .imperfectivePastPlural(MotherCreator.random().animal().name())
      .imperfectiveFutureFirstPersonSingular(MotherCreator.random().animal().name())
      .imperfectiveFutureSecondPersonSingular(MotherCreator.random().animal().name())
      .imperfectiveFutureThirdPersonSingular(MotherCreator.random().animal().name())
      .imperfectiveFutureFirstPersonPlural(MotherCreator.random().animal().name())
      .imperfectiveFutureSecondPersonPlural(MotherCreator.random().animal().name())
      .imperfectiveFutureThirdPersonPlural(MotherCreator.random().animal().name())
      .perfectivePastMasculine(MotherCreator.random().animal().name())
      .perfectivePastFeminine(MotherCreator.random().animal().name())
      .perfectivePastNeuter(MotherCreator.random().animal().name())
      .perfectivePastPlural(MotherCreator.random().animal().name())
      .perfectiveFutureFirstPersonSingular(MotherCreator.random().animal().name())
      .perfectiveFutureSecondPersonSingular(MotherCreator.random().animal().name())
      .perfectiveFutureThirdPersonSingular(MotherCreator.random().animal().name())
      .perfectiveFutureFirstPersonPlural(MotherCreator.random().animal().name())
      .perfectiveFutureSecondPersonPlural(MotherCreator.random().animal().name())
      .perfectiveFutureThirdPersonPlural(MotherCreator.random().animal().name());
  }

}
