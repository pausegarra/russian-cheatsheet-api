package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordConjugationEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordDeclinationEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordDeclinationMatrixEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.ConjugationsRequired;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.DeclinationsRequired;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class CreateWordUseCase implements UseCase<CreateWordDto, WordDto> {

  private final WordsRepository wordsRepository;

  @Transactional
  @Override
  public WordDto handle(CreateWordDto dto) {
    WordEntity wordEntity = WordEntity.create(dto.russian(), dto.english(), dto.spanish(), dto.type());

    WordEntity saved = wordsRepository.create(wordEntity);

    if (saved.canHaveConjugations()) {
      WordConjugationEntity conjugations = createConjugations(dto.conjugations());
      WordEntity wordWithConjugations = saved.addConjugations(conjugations);

      return WordDto.fromEntity(wordsRepository.save(wordWithConjugations));
    }

    if (saved.canHaveDeclinations()) {
      WordDeclinationEntity declinations = createDeclinations(dto.declinations());
      WordEntity wordWithDeclinations = saved.addDeclinations(declinations);

      return WordDto.fromEntity(wordsRepository.save(wordWithDeclinations));
    }

    if (saved.canHaveDeclinationMatrix()) {
      WordDeclinationMatrixEntity declinationMatrix = createDeclinationMatrix(dto.declinationMatrix());
      WordEntity wordWithDeclinationMatrix = saved.addDeclinationMatrix(declinationMatrix);

      return WordDto.fromEntity(wordsRepository.save(wordWithDeclinationMatrix));
    }

    return WordDto.fromEntity(saved);
  }

  private WordDeclinationMatrixEntity createDeclinationMatrix(CreateWordDeclinationMatrixDto dto) {
    if (dto == null) {
      throw new DeclinationsRequired();
    }

    return WordDeclinationMatrixEntity.create(
      dto.nominativeMasculine(),
      dto.nominativeFeminine(),
      dto.nominativeNeuter(),
      dto.nominativePlural(),
      dto.accusativeMasculine(),
      dto.accusativeFeminine(),
      dto.accusativeNeuter(),
      dto.accusativePlural(),
      dto.genitiveMasculine(),
      dto.genitiveFeminine(),
      dto.genitiveNeuter(),
      dto.genitivePlural(),
      dto.dativeMasculine(),
      dto.dativeFeminine(),
      dto.dativeNeuter(),
      dto.dativePlural(),
      dto.instrumentalMasculine(),
      dto.instrumentalFeminine(),
      dto.instrumentalNeuter(),
      dto.instrumentalPlural(),
      dto.prepositionalMasculine(),
      dto.prepositionalFeminine(),
      dto.prepositionalNeuter(),
      dto.prepositionalPlural()
    );
  }

  private WordConjugationEntity createConjugations(CreateWordConjugationDto dto) {
    if (dto == null) {
      throw new ConjugationsRequired();
    }

    return WordConjugationEntity.create(
      dto.imperfectivePresentFirstPersonSingular(),
      dto.imperfectivePresentSecondPersonSingular(),
      dto.imperfectivePresentThirdPersonSingular(),
      dto.imperfectivePresentFirstPersonPlural(),
      dto.imperfectivePresentSecondPersonPlural(),
      dto.imperfectivePresentThirdPersonPlural(),
      dto.imperfectivePastMasculine(),
      dto.imperfectivePastFeminine(),
      dto.imperfectivePastNeuter(),
      dto.imperfectivePastPlural(),
      dto.imperfectiveFutureFirstPersonSingular(),
      dto.imperfectiveFutureSecondPersonSingular(),
      dto.imperfectiveFutureThirdPersonSingular(),
      dto.imperfectiveFutureFirstPersonPlural(),
      dto.imperfectiveFutureSecondPersonPlural(),
      dto.imperfectiveFutureThirdPersonPlural(),
      dto.perfectivePastMasculine(),
      dto.perfectivePastFeminine(),
      dto.perfectivePastNeuter(),
      dto.perfectivePastPlural(),
      dto.perfectiveFutureFirstPersonSingular(),
      dto.perfectiveFutureSecondPersonSingular(),
      dto.perfectiveFutureThirdPersonSingular(),
      dto.perfectiveFutureFirstPersonPlural(),
      dto.perfectiveFutureSecondPersonPlural(),
      dto.perfectiveFutureThirdPersonPlural()
    );
  }

  private WordDeclinationEntity createDeclinations(CreateWordDeclinationDto dto) {
    if (dto == null) {
      throw new DeclinationsRequired();
    }

    return WordDeclinationEntity.create(
      dto.nominative(),
      dto.accusative(),
      dto.genitive(),
      dto.dative(),
      dto.instrumental(),
      dto.prepositional(),
      dto.nominativePlural(),
      dto.accusativePlural(),
      dto.genitivePlural(),
      dto.dativePlural(),
      dto.instrumentalPlural(),
      dto.prepositionalPlural()
    );
  }

}
