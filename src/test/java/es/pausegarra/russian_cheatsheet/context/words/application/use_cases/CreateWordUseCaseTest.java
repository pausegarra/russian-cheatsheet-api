package es.pausegarra.russian_cheatsheet.context.words.application.use_cases;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.requests.create_word.CreateWordConjugationDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.requests.create_word.CreateWordDeclinationDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.requests.create_word.CreateWordDeclinationMatrixDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.requests.create_word.CreateWordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.responses.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.services.WordsCommandService;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordConjugationEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordDeclinationEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordDeclinationMatrixEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import es.pausegarra.russian_cheatsheet.mother.WordConjugationMother;
import es.pausegarra.russian_cheatsheet.mother.WordDeclinationMatrixMother;
import es.pausegarra.russian_cheatsheet.mother.WordDeclinationMother;
import es.pausegarra.russian_cheatsheet.mother.WordMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateWordUseCaseTest {

  @Mock
  private WordsCommandService wordsCommandService;

  @InjectMocks
  private CreateWordUseCase createWordUseCase;

  @Test
  public void shouldHandle() {
    WordEntity word = WordMother.random().type(WordType.OTHER).build();
    when(wordsCommandService.create(any(CreateWordDto.class))).thenReturn(word);

    CreateWordDto dto = new CreateWordDto(
      word.russian(),
      word.english(),
      word.spanish(),
      word.type(),
      null,
      null,
      null
    );
    WordDto result = createWordUseCase.handle(dto);

    assertNotNull(result);
    assertEquals(word.russian(), result.russian());
    assertEquals(word.english(), result.english());
    assertEquals(word.spanish(), result.spanish());
    assertEquals(word.type(), result.type());
    assertEquals(word.createdBy(), result.createdBy());
    assertEquals(word.createdAt(), result.createdAt());
    assertEquals(word.updatedBy(), result.updatedBy());
    assertEquals(word.updatedAt(), result.updatedAt());
  }

  @Test
  public void shouldHandleWithConjugations() {
    WordConjugationEntity conjugations = WordConjugationMother.random().build();
    WordEntity word = WordMother.random()
      .type(WordType.VERB)
      .conjugations(conjugations)
      .build();
    when(wordsCommandService.create(any(CreateWordDto.class))).thenReturn(word);

    CreateWordDto dto = new CreateWordDto(
      word.russian(),
      word.english(),
      word.spanish(),
      word.type(),
      new CreateWordConjugationDto(
        conjugations.imperfectivePresentFirstPersonSingular(),
        conjugations.imperfectivePresentSecondPersonSingular(),
        conjugations.imperfectivePresentThirdPersonSingular(),
        conjugations.imperfectivePresentFirstPersonPlural(),
        conjugations.imperfectivePresentSecondPersonPlural(),
        conjugations.imperfectivePresentThirdPersonPlural(),
        conjugations.imperfectivePastMasculine(),
        conjugations.imperfectivePastFeminine(),
        conjugations.imperfectivePastNeuter(),
        conjugations.imperfectivePastPlural(),
        conjugations.imperfectiveFutureFirstPersonSingular(),
        conjugations.imperfectiveFutureSecondPersonSingular(),
        conjugations.imperfectiveFutureThirdPersonSingular(),
        conjugations.imperfectiveFutureFirstPersonPlural(),
        conjugations.imperfectiveFutureSecondPersonPlural(),
        conjugations.imperfectiveFutureThirdPersonPlural(),
        conjugations.perfectivePastMasculine(),
        conjugations.perfectivePastFeminine(),
        conjugations.perfectivePastNeuter(),
        conjugations.perfectivePastPlural(),
        conjugations.perfectiveFutureFirstPersonSingular(),
        conjugations.perfectiveFutureSecondPersonSingular(),
        conjugations.perfectiveFutureThirdPersonSingular(),
        conjugations.perfectiveFutureFirstPersonPlural(),
        conjugations.perfectiveFutureSecondPersonPlural(),
        conjugations.perfectiveFutureThirdPersonPlural()
      ),
      null,
      null
    );
    WordDto result = createWordUseCase.handle(dto);

    assertNotNull(result);
    assertEquals(word.russian(), result.russian());
    assertEquals(word.english(), result.english());
    assertEquals(word.spanish(), result.spanish());
    assertEquals(word.type(), result.type());
    assertEquals(word.createdBy(), result.createdBy());
    assertEquals(word.createdAt(), result.createdAt());
    assertEquals(word.updatedBy(), result.updatedBy());
    assertEquals(word.updatedAt(), result.updatedAt());
    assertNotNull(result.conjugations());
  }

  @Test
  public void shouldHandleWithDeclinations() {
    WordDeclinationEntity declinations = WordDeclinationMother.random().build();
    WordEntity word = WordMother.random()
      .type(WordType.NOUN)
      .declinations(declinations)
      .build();
    when(wordsCommandService.create(any(CreateWordDto.class))).thenReturn(word);

    CreateWordDto dto = new CreateWordDto(
      word.russian(),
      word.english(),
      word.spanish(),
      word.type(),
      null,
      new CreateWordDeclinationDto(
        declinations.nominative(),
        declinations.accusative(),
        declinations.genitive(),
        declinations.dative(),
        declinations.instrumental(),
        declinations.prepositional()
      ),
      null
    );
    WordDto result = createWordUseCase.handle(dto);

    assertNotNull(result);
    assertEquals(word.russian(), result.russian());
    assertEquals(word.english(), result.english());
    assertEquals(word.spanish(), result.spanish());
    assertEquals(word.type(), result.type());
    assertEquals(word.createdBy(), result.createdBy());
    assertEquals(word.createdAt(), result.createdAt());
    assertEquals(word.updatedBy(), result.updatedBy());
    assertEquals(word.updatedAt(), result.updatedAt());
    assertNotNull(result.declinations());
  }

  @Test
  public void shouldHandleWithDeclinationMatrix() {
    WordDeclinationMatrixEntity declinations = WordDeclinationMatrixMother.random().build();
    WordEntity word = WordMother.random()
      .type(WordType.ADJECTIVE)
      .declinationMatrix(declinations)
      .build();
    when(wordsCommandService.create(any(CreateWordDto.class))).thenReturn(word);

    CreateWordDto dto = new CreateWordDto(
      word.russian(),
      word.english(),
      word.spanish(),
      word.type(),
      null,
      null,
      new CreateWordDeclinationMatrixDto(
        declinations.nominativeMasculine(),
        declinations.nominativeFeminine(),
        declinations.nominativeNeuter(),
        declinations.nominativePlural(),
        declinations.accusativeMasculine(),
        declinations.accusativeFeminine(),
        declinations.accusativeNeuter(),
        declinations.accusativePlural(),
        declinations.genitiveMasculine(),
        declinations.genitiveFeminine(),
        declinations.genitiveNeuter(),
        declinations.genitivePlural(),
        declinations.dativeMasculine(),
        declinations.dativeFeminine(),
        declinations.dativeNeuter(),
        declinations.dativePlural(),
        declinations.instrumentalMasculine(),
        declinations.instrumentalFeminine(),
        declinations.instrumentalNeuter(),
        declinations.instrumentalPlural(),
        declinations.prepositionalMasculine(),
        declinations.prepositionalFeminine(),
        declinations.prepositionalNeuter(),
        declinations.prepositionalPlural()
      )
    );
    WordDto result = createWordUseCase.handle(dto);

    assertNotNull(result);
    assertEquals(word.russian(), result.russian());
    assertEquals(word.english(), result.english());
    assertEquals(word.spanish(), result.spanish());
    assertEquals(word.type(), result.type());
    assertEquals(word.createdBy(), result.createdBy());
    assertEquals(word.createdAt(), result.createdAt());
    assertEquals(word.updatedBy(), result.updatedBy());
    assertEquals(word.updatedAt(), result.updatedAt());
    assertNotNull(result.declinationMatrix());
  }

}