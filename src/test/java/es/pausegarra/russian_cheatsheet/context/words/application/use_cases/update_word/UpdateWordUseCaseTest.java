package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_word;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordDeclinationMatrixEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.ConjugationsRequired;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.DeclinationsRequired;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordsRepository;
import es.pausegarra.russian_cheatsheet.mother.WordConjugationMother;
import es.pausegarra.russian_cheatsheet.mother.WordDeclinationMatrixMother;
import es.pausegarra.russian_cheatsheet.mother.WordDeclinationMother;
import es.pausegarra.russian_cheatsheet.mother.WordMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateWordUseCaseTest {

  @Mock
  private WordsRepository wordsRepository;

  @InjectMocks
  private UpdateWordUseCase wordsCommandService;

  @Test
  void shouldUpdateWord() {
    WordEntity wordEntity = WordMother.random().type(WordType.OTHER).build();
    when(wordsRepository.findById(any(UUID.class))).thenReturn(Optional.of(wordEntity));
    when(wordsRepository.save(any(WordEntity.class))).thenReturn(wordEntity);

    UpdateWordDto dto = new UpdateWordDto(
      wordEntity.id(),
      wordEntity.russian(),
      wordEntity.spanish(),
      wordEntity.english(),
      wordEntity.type(),
      null,
      null,
      null
    );
    WordDto word = wordsCommandService.handle(dto);

    assertNotNull(word);
    assertEquals(dto.id(), word.id());
    assertEquals(dto.russian(), word.russian());
    assertEquals(dto.english(), word.english());
    assertEquals(dto.spanish(), word.spanish());
    assertEquals(dto.type(), word.type());

    assertNull(word.conjugations());
    assertNull(word.declinations());
    assertNull(word.declinationMatrix());

    verify(wordsRepository).findById(any(UUID.class));
    verify(wordsRepository).save(any(WordEntity.class));
  }

  @Test
  public void shouldUpdateWordWithConjugations() {
    WordEntity wordEntity = WordMother.random()
      .type(WordType.NOUN)
      .declinations(WordDeclinationMother.random().build())
      .build();
    when(wordsRepository.findById(any(UUID.class))).thenReturn(Optional.of(wordEntity));

    WordEntity updated = WordMother.random()
      .type(WordType.VERB)
      .conjugations(WordConjugationMother.random().build())
      .build();
    when(wordsRepository.save(any(WordEntity.class))).thenReturn(updated);

    UpdateWordDto dto = new UpdateWordDto(
      updated.id(),
      updated.russian(),
      updated.spanish(),
      updated.english(),
      updated.type(),
      null,
      new UpdateWordConjugationsDto(
        updated.conjugations().imperfectivePresentFirstPersonSingular(),
        updated.conjugations().imperfectivePresentSecondPersonSingular(),
        updated.conjugations().imperfectivePresentThirdPersonSingular(),
        updated.conjugations().imperfectivePresentFirstPersonPlural(),
        updated.conjugations().imperfectivePresentSecondPersonPlural(),
        updated.conjugations().imperfectivePresentThirdPersonPlural(),
        updated.conjugations().imperfectivePastMasculine(),
        updated.conjugations().imperfectivePastFeminine(),
        updated.conjugations().imperfectivePastNeuter(),
        updated.conjugations().imperfectivePastPlural(),
        updated.conjugations().imperfectiveFutureFirstPersonSingular(),
        updated.conjugations().imperfectiveFutureSecondPersonSingular(),
        updated.conjugations().imperfectiveFutureThirdPersonSingular(),
        updated.conjugations().imperfectiveFutureFirstPersonPlural(),
        updated.conjugations().imperfectiveFutureSecondPersonPlural(),
        updated.conjugations().imperfectiveFutureThirdPersonPlural(),
        updated.conjugations().perfectivePastMasculine(),
        updated.conjugations().perfectivePastFeminine(),
        updated.conjugations().perfectivePastNeuter(),
        updated.conjugations().perfectivePastPlural(),
        updated.conjugations().perfectiveFutureFirstPersonSingular(),
        updated.conjugations().perfectiveFutureSecondPersonSingular(),
        updated.conjugations().perfectiveFutureThirdPersonSingular(),
        updated.conjugations().perfectiveFutureFirstPersonPlural(),
        updated.conjugations().perfectiveFutureSecondPersonPlural(),
        updated.conjugations().perfectiveFutureThirdPersonPlural()
      ),
      null
    );
    WordDto result = wordsCommandService.handle(dto);

    assertNotNull(result);
    assertEquals(dto.id(), result.id());
    assertEquals(dto.russian(), result.russian());
    assertEquals(dto.english(), result.english());
    assertEquals(dto.spanish(), result.spanish());
    assertEquals(dto.type(), result.type());
    assertNull(result.declinations());
    assertNull(result.declinationMatrix());

    assertNotNull(result.conjugations());
    assertEquals(updated.conjugations().imperfectivePresentFirstPersonSingular(), result.conjugations().imperfectivePresentFirstPersonSingular());
    assertEquals(updated.conjugations().imperfectivePresentSecondPersonSingular(), result.conjugations().imperfectivePresentSecondPersonSingular());
    assertEquals(updated.conjugations().imperfectivePresentThirdPersonSingular(), result.conjugations().imperfectivePresentThirdPersonSingular());
    assertEquals(updated.conjugations().imperfectivePresentFirstPersonPlural(), result.conjugations().imperfectivePresentFirstPersonPlural());
    assertEquals(updated.conjugations().imperfectivePresentSecondPersonPlural(), result.conjugations().imperfectivePresentSecondPersonPlural());
    assertEquals(updated.conjugations().imperfectivePresentThirdPersonPlural(), result.conjugations().imperfectivePresentThirdPersonPlural());
    assertEquals(updated.conjugations().imperfectivePastMasculine(), result.conjugations().imperfectivePastMasculine());
    assertEquals(updated.conjugations().imperfectivePastFeminine(), result.conjugations().imperfectivePastFeminine());
    assertEquals(updated.conjugations().imperfectivePastNeuter(), result.conjugations().imperfectivePastNeuter());
    assertEquals(updated.conjugations().imperfectivePastPlural(), result.conjugations().imperfectivePastPlural());
    assertEquals(updated.conjugations().imperfectiveFutureFirstPersonSingular(), result.conjugations().imperfectiveFutureFirstPersonSingular());
    assertEquals(updated.conjugations().imperfectiveFutureSecondPersonSingular(), result.conjugations().imperfectiveFutureSecondPersonSingular());
    assertEquals(updated.conjugations().imperfectiveFutureThirdPersonSingular(), result.conjugations().imperfectiveFutureThirdPersonSingular());
    assertEquals(updated.conjugations().imperfectiveFutureFirstPersonPlural(), result.conjugations().imperfectiveFutureFirstPersonPlural());
    assertEquals(updated.conjugations().imperfectiveFutureSecondPersonPlural(), result.conjugations().imperfectiveFutureSecondPersonPlural());
    assertEquals(updated.conjugations().imperfectiveFutureThirdPersonPlural(), result.conjugations().imperfectiveFutureThirdPersonPlural());
    assertEquals(updated.conjugations().perfectivePastMasculine(), result.conjugations().perfectivePastMasculine());
    assertEquals(updated.conjugations().perfectivePastFeminine(), result.conjugations().perfectivePastFeminine());
    assertEquals(updated.conjugations().perfectivePastNeuter(), result.conjugations().perfectivePastNeuter());
    assertEquals(updated.conjugations().perfectivePastPlural(), result.conjugations().perfectivePastPlural());
    assertEquals(updated.conjugations().perfectiveFutureFirstPersonSingular(), result.conjugations().perfectiveFutureFirstPersonSingular());
    assertEquals(updated.conjugations().perfectiveFutureSecondPersonSingular(), result.conjugations().perfectiveFutureSecondPersonSingular());
    assertEquals(updated.conjugations().perfectiveFutureThirdPersonSingular(), result.conjugations().perfectiveFutureThirdPersonSingular());
    assertEquals(updated.conjugations().perfectiveFutureFirstPersonPlural(), result.conjugations().perfectiveFutureFirstPersonPlural());
    assertEquals(updated.conjugations().perfectiveFutureSecondPersonPlural(), result.conjugations().perfectiveFutureSecondPersonPlural());
    assertEquals(updated.conjugations().perfectiveFutureThirdPersonPlural(), result.conjugations().perfectiveFutureThirdPersonPlural());

    verify(wordsRepository).findById(any(UUID.class));
    verify(wordsRepository).save(any(WordEntity.class));
  }

  @Test
  public void shouldUpdateWordWithDeclinations() {
    WordEntity wordEntity = WordMother.random()
      .type(WordType.VERB)
      .conjugations(WordConjugationMother.random().build())
      .build();
    when(wordsRepository.findById(any(UUID.class))).thenReturn(Optional.of(wordEntity));

    WordEntity updated = WordMother.random()
      .type(WordType.NOUN)
      .declinations(WordDeclinationMother.random().build())
      .build();
    when(wordsRepository.save(any(WordEntity.class))).thenReturn(updated);

    UpdateWordDto dto = new UpdateWordDto(
      updated.id(),
      updated.russian(),
      updated.spanish(),
      updated.english(),
      updated.type(),
      new UpdateWordDeclinationDto(
        updated.declinations().nominative(),
        updated.declinations().accusative(),
        updated.declinations().genitive(),
        updated.declinations().dative(),
        updated.declinations().instrumental(),
        updated.declinations().prepositional()
      ),
      null,
      null
    );
    WordDto result = wordsCommandService.handle(dto);

    assertNotNull(result);
    assertEquals(dto.id(), result.id());
    assertEquals(dto.russian(), result.russian());
    assertEquals(dto.english(), result.english());
    assertEquals(dto.spanish(), result.spanish());
    assertEquals(dto.type(), result.type());
    assertNull(result.conjugations());
    assertNull(result.declinationMatrix());

    assertNotNull(result.declinations());
    assertEquals(updated.declinations().nominative(), result.declinations().nominative());
    assertEquals(updated.declinations().accusative(), result.declinations().accusative());
    assertEquals(updated.declinations().genitive(), result.declinations().genitive());
    assertEquals(updated.declinations().dative(), result.declinations().dative());
    assertEquals(updated.declinations().instrumental(), result.declinations().instrumental());
    assertEquals(updated.declinations().prepositional(), result.declinations().prepositional());

    verify(wordsRepository).findById(any(UUID.class));
    verify(wordsRepository).save(any(WordEntity.class));
  }

  @Test
  public void shouldUpdateWordWithDeclinationMatrix() {
    WordEntity wordEntity = WordMother.random()
      .type(WordType.NOUN)
      .conjugations(WordConjugationMother.random().build())
      .build();
    when(wordsRepository.findById(any(UUID.class))).thenReturn(Optional.of(wordEntity));

    WordEntity updated = WordMother.random()
      .type(WordType.ADJECTIVE)
      .declinationMatrix(WordDeclinationMatrixMother.random().build())
      .build();
    when(wordsRepository.save(any(WordEntity.class))).thenReturn(updated);

    UpdateWordDto dto = new UpdateWordDto(
      updated.id(),
      updated.russian(),
      updated.spanish(),
      updated.english(),
      updated.type(),
      null,
      null,
      new UpdateWordDeclinationMatrixDto(
        updated.declinationMatrix().nominativeMasculine(),
        updated.declinationMatrix().nominativeFeminine(),
        updated.declinationMatrix().nominativeNeuter(),
        updated.declinationMatrix().nominativePlural(),
        updated.declinationMatrix().accusativeMasculine(),
        updated.declinationMatrix().accusativeFeminine(),
        updated.declinationMatrix().accusativeNeuter(),
        updated.declinationMatrix().accusativePlural(),
        updated.declinationMatrix().genitiveMasculine(),
        updated.declinationMatrix().genitiveFeminine(),
        updated.declinationMatrix().genitiveNeuter(),
        updated.declinationMatrix().genitivePlural(),
        updated.declinationMatrix().dativeMasculine(),
        updated.declinationMatrix().dativeFeminine(),
        updated.declinationMatrix().dativeNeuter(),
        updated.declinationMatrix().dativePlural(),
        updated.declinationMatrix().instrumentalMasculine(),
        updated.declinationMatrix().instrumentalFeminine(),
        updated.declinationMatrix().instrumentalNeuter(),
        updated.declinationMatrix().instrumentalPlural(),
        updated.declinationMatrix().prepositionalMasculine(),
        updated.declinationMatrix().prepositionalFeminine(),
        updated.declinationMatrix().prepositionalNeuter(),
        updated.declinationMatrix().prepositionalPlural()
      )
    );
    WordDto result = wordsCommandService.handle(dto);

    assertNotNull(result);
    assertEquals(dto.id(), result.id());
    assertEquals(dto.russian(), result.russian());
    assertEquals(dto.english(), result.english());
    assertEquals(dto.spanish(), result.spanish());
    assertEquals(dto.type(), result.type());
    assertNull(result.conjugations());
    assertNull(result.declinations());

    assertNotNull(result.declinationMatrix());
    assertEquals(updated.declinationMatrix().nominativeMasculine(), result.declinationMatrix().nominativeMasculine());
    assertEquals(updated.declinationMatrix().nominativeFeminine(), result.declinationMatrix().nominativeFeminine());
    assertEquals(updated.declinationMatrix().nominativeNeuter(), result.declinationMatrix().nominativeNeuter());
    assertEquals(updated.declinationMatrix().nominativePlural(), result.declinationMatrix().nominativePlural());
    assertEquals(updated.declinationMatrix().accusativeMasculine(), result.declinationMatrix().accusativeMasculine());
    assertEquals(updated.declinationMatrix().accusativeFeminine(), result.declinationMatrix().accusativeFeminine());
    assertEquals(updated.declinationMatrix().accusativeNeuter(), result.declinationMatrix().accusativeNeuter());
    assertEquals(updated.declinationMatrix().accusativePlural(), result.declinationMatrix().accusativePlural());
    assertEquals(updated.declinationMatrix().genitiveMasculine(), result.declinationMatrix().genitiveMasculine());
    assertEquals(updated.declinationMatrix().genitiveFeminine(), result.declinationMatrix().genitiveFeminine());
    assertEquals(updated.declinationMatrix().genitiveNeuter(), result.declinationMatrix().genitiveNeuter());
    assertEquals(updated.declinationMatrix().genitivePlural(), result.declinationMatrix().genitivePlural());
    assertEquals(updated.declinationMatrix().dativeMasculine(), result.declinationMatrix().dativeMasculine());
    assertEquals(updated.declinationMatrix().dativeFeminine(), result.declinationMatrix().dativeFeminine());
    assertEquals(updated.declinationMatrix().dativeNeuter(), result.declinationMatrix().dativeNeuter());
    assertEquals(updated.declinationMatrix().dativePlural(), result.declinationMatrix().dativePlural());
    assertEquals(updated.declinationMatrix().instrumentalMasculine(), result.declinationMatrix().instrumentalMasculine());
    assertEquals(updated.declinationMatrix().instrumentalFeminine(), result.declinationMatrix().instrumentalFeminine());
    assertEquals(updated.declinationMatrix().instrumentalNeuter(), result.declinationMatrix().instrumentalNeuter());
    assertEquals(updated.declinationMatrix().instrumentalPlural(), result.declinationMatrix().instrumentalPlural());
    assertEquals(updated.declinationMatrix().prepositionalMasculine(), result.declinationMatrix().prepositionalMasculine());
    assertEquals(updated.declinationMatrix().prepositionalFeminine(), result.declinationMatrix().prepositionalFeminine());
    assertEquals(updated.declinationMatrix().prepositionalNeuter(), result.declinationMatrix().prepositionalNeuter());
    assertEquals(updated.declinationMatrix().prepositionalPlural(), result.declinationMatrix().prepositionalPlural());

    verify(wordsRepository).findById(any(UUID.class));
    verify(wordsRepository).save(any(WordEntity.class));
  }

  @Test
  public void shouldThrowExceptionWhenUpdatingWordToVerbWithoutConjugations() {
    WordEntity wordEntity = WordMother.random()
      .type(WordType.NOUN)
      .build();
    when(wordsRepository.findById(any(UUID.class))).thenReturn(Optional.of(wordEntity));

    UpdateWordDto dto = new UpdateWordDto(
      wordEntity.id(),
      wordEntity.russian(),
      wordEntity.spanish(),
      wordEntity.english(),
      WordType.VERB,
      null,
      null,
      null
    );
    assertThrows(ConjugationsRequired.class, () -> wordsCommandService.handle(dto));
  }

  @Test
  public void shouldThrowExceptionWhenUpdatingWordToNounWithoutDeclinations() {
    WordEntity wordEntity = WordMother.random()
      .type(WordType.VERB)
      .build();
    when(wordsRepository.findById(any(UUID.class))).thenReturn(Optional.of(wordEntity));

    UpdateWordDto dto = new UpdateWordDto(
      wordEntity.id(),
      wordEntity.russian(),
      wordEntity.spanish(),
      wordEntity.english(),
      WordType.NOUN,
      null,
      null,
      null
    );
    assertThrows(DeclinationsRequired.class, () -> wordsCommandService.handle(dto));
  }

  @Test
  public void shouldThrowExceptionWhenUpdatingWordToAdjectiveWithoutDeclinationMatrix() {
    WordEntity wordEntity = WordMother.random()
      .type(WordType.NOUN)
      .build();
    when(wordsRepository.findById(any(UUID.class))).thenReturn(Optional.of(wordEntity));

    UpdateWordDto dto = new UpdateWordDto(
      wordEntity.id(),
      wordEntity.russian(),
      wordEntity.spanish(),
      wordEntity.english(),
      WordType.ADJECTIVE,
      null,
      null,
      null
    );
    assertThrows(DeclinationsRequired.class, () -> wordsCommandService.handle(dto));
  }

}