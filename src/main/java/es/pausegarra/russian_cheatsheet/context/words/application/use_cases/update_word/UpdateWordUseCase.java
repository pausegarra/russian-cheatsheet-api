package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_word;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordConjugationEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordDeclinationEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordDeclinationMatrixEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.ConjugationsRequired;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.DeclinationsRequired;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.WordNotFound;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class UpdateWordUseCase implements UseCase<UpdateWordDto, WordDto> {

  private final WordsRepository wordsRepository;

  @Override
  @Transactional
  public WordDto handle(UpdateWordDto dto) {
    WordEntity word = wordsRepository.findById(dto.id())
      .orElseThrow(() -> new WordNotFound(dto.id().toString()));

    WordEntity updated = word.update(
      dto.russian(),
      dto.english(),
      dto.spanish(),
      dto.type()
    );

    if (updated.canHaveConjugations()) {
      WordConjugationEntity conjugation = createConjugations(dto.conjugations());
      WordEntity wordWithConjugations = updated.addConjugations(conjugation);

      return WordDto.fromEntity(wordsRepository.save(wordWithConjugations));
    }

    if (updated.canHaveDeclinations()) {
      WordDeclinationEntity declination = createDeclinations(dto.declinations());
      WordEntity wordWithDeclinations = updated.addDeclinations(declination);

      return WordDto.fromEntity(wordsRepository.save(wordWithDeclinations));
    }

    if (updated.canHaveDeclinationMatrix()) {
      WordDeclinationMatrixEntity declinationMatrix = createDeclinationMatrix(dto.declinationMatrix());
      WordEntity wordWithDeclinationMatrix = updated.addDeclinationMatrix(declinationMatrix);

      return WordDto.fromEntity(wordsRepository.save(wordWithDeclinationMatrix));
    }

    return WordDto.fromEntity(wordsRepository.save(updated));
  }

  private WordDeclinationMatrixEntity createDeclinationMatrix(UpdateWordDeclinationMatrixDto dto) {
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

  private WordConjugationEntity createConjugations(UpdateWordConjugationsDto dto) {
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

  private WordDeclinationEntity createDeclinations(UpdateWordDeclinationDto dto) {
    if (dto == null) {
      throw new DeclinationsRequired();
    }

    return WordDeclinationEntity.create(
      dto.nominative(),
      dto.accusative(),
      dto.genitive(),
      dto.dative(),
      dto.instrumental(),
      dto.prepositional()
    );
  }

}
