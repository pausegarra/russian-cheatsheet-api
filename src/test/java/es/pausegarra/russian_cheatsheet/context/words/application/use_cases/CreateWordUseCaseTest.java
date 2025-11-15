package es.pausegarra.russian_cheatsheet.context.words.application.use_cases;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word.*;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordConjugationEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordDeclinationEntity;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateWordUseCaseTest {

  @Mock
  private WordsRepository wordsRepository;

  @InjectMocks
  private CreateWordUseCase wordsCommandService;

  @Test
  public void shouldCreateWord() {
    WordEntity wordEntity = WordMother.random().type(WordType.OTHER).build();
    when(wordsRepository.create(any(WordEntity.class))).thenReturn(wordEntity);

    CreateWordDto dto = new CreateWordDto(
      wordEntity.russian(),
      wordEntity.english(),
      wordEntity.spanish(),
      wordEntity.type(),
      null,
      null,
      null
    );
    WordDto word = wordsCommandService.handle(dto);

    assertNotNull(word);
    assertEquals(dto.russian(), word.russian());
    assertEquals(dto.english(), word.english());
    assertEquals(dto.spanish(), word.spanish());
    assertEquals(dto.type(), word.type());

    verify(wordsRepository).create(any(WordEntity.class));
  }

  @Test
  public void shouldCreateWordWithConjugationsWhenTypeIsVerb() {
    WordEntity wordEntity = WordMother.random().type(WordType.VERB).build();
    when(wordsRepository.create(any(WordEntity.class))).thenReturn(wordEntity);

    WordConjugationEntity conjugations = WordConjugationMother.random().build();
    when(wordsRepository.save(any(WordEntity.class))).thenReturn(wordEntity.withConjugations(conjugations));

    CreateWordDto dto = new CreateWordDto(
      wordEntity.russian(), wordEntity.english(), wordEntity.spanish(), wordEntity.type(), new CreateWordConjugationDto(
      "imperfectivePresentFirstPersonSingular",
      "imperfectivePresentSecondPersonSingular",
      "imperfectivePresentThirdPersonSingular",
      "imperfectivePresentFirstPersonPlural",
      "imperfectivePresentSecondPersonPlural",
      "imperfectivePresentThirdPersonPlural",
      "imperfectivePastMasculine",
      "imperfectivePastFeminine",
      "imperfectivePastNeuter",
      "imperfectivePastPlural",
      "imperfectiveFutureFirstPersonSingular",
      "imperfectiveFutureSecondPersonSingular",
      "imperfectiveFutureThirdPersonSingular",
      "imperfectiveFutureFirstPersonPlural",
      "imperfectiveFutureSecondPersonPlural",
      "imperfectiveFutureThirdPersonPlural",
      "perfectivePastMasculine",
      "perfectivePastFeminine",
      "perfectivePastNeuter",
      "perfectivePastPlural",
      "perfectiveFutureFirstPersonSingular",
      "perfectiveFutureSecondPersonSingular",
      "perfectiveFutureThirdPersonSingular",
      "perfectiveFutureFirstPersonPlural",
      "perfectiveFutureSecondPersonPlural",
      "perfectiveFutureThirdPersonPlural"
    ), null, null
    );
    WordDto word = wordsCommandService.handle(dto);

    assertNotNull(word);
    assertEquals(dto.russian(), word.russian());
    assertEquals(dto.english(), word.english());
    assertEquals(dto.spanish(), word.spanish());
    assertEquals(dto.type(), word.type());
    assertNotNull(word.conjugations());

    verify(wordsRepository).create(any(WordEntity.class));
    verify(wordsRepository).save(any(WordEntity.class));
  }

  @Test
  public void shouldCreateWordWithDeclinationsWhenTypeIsNoun() {
    WordEntity wordEntity = WordMother.random().type(WordType.NOUN).build();
    when(wordsRepository.create(any(WordEntity.class))).thenReturn(wordEntity);

    WordDeclinationEntity declinations = WordDeclinationMother.random().build();
    when(wordsRepository.save(any(WordEntity.class))).thenReturn(wordEntity.withDeclinations(declinations));

    CreateWordDto dto = new CreateWordDto(
      wordEntity.russian(),
      wordEntity.english(),
      wordEntity.spanish(),
      wordEntity.type(),
      null,
      new CreateWordDeclinationDto("nominative", "accusative", "genitive", "dative", "instrumental", "prepositional"),
      null
    );
    WordDto word = wordsCommandService.handle(dto);

    assertNotNull(word);
    assertEquals(dto.russian(), word.russian());
    assertEquals(dto.english(), word.english());
    assertEquals(dto.spanish(), word.spanish());
    assertEquals(dto.type(), word.type());
    assertNotNull(word.declinations());

    verify(wordsRepository).create(any(WordEntity.class));
    verify(wordsRepository).save(any(WordEntity.class));
  }

  @Test
  public void shouldCreateWordWithDeclinationMatrixWhenTypeIsAdjectiveOrPronounOrParticipleOrOrdinal() {
    WordEntity wordEntity = WordMother.random().type(WordType.ADJECTIVE).build();
    when(wordsRepository.create(any(WordEntity.class))).thenReturn(wordEntity);

    WordDeclinationMatrixEntity declinationMatrix = WordDeclinationMatrixMother.random().build();
    when(wordsRepository.save(any(WordEntity.class))).thenReturn(wordEntity.withDeclinationMatrix(declinationMatrix));

    CreateWordDto dto = new CreateWordDto(
      wordEntity.russian(), wordEntity.english(), wordEntity.spanish(), wordEntity.type(), null, null, new CreateWordDeclinationMatrixDto(
      "nominativeMasculine",
      "nominativeFeminine",
      "nominativeNeuter",
      "nominativePlural",
      "accusativeMasculine",
      "accusativeFeminine",
      "accusativeNeuter",
      "accusativePlural",
      "genitiveMasculine",
      "genitiveFeminine",
      "genitiveNeuter",
      "genitivePlural",
      "dativeMasculine",
      "dativeFeminine",
      "dativeNeuter",
      "dativePlural",
      "instrumentalMasculine",
      "instrumentalFeminine",
      "instrumentalNeuter",
      "instrumentalPlural",
      "prepositionalMasculine",
      "prepositionalFeminine",
      "prepositionalNeuter",
      "prepositionalPlural"
    )
    );
    WordDto word = wordsCommandService.handle(dto);

    assertNotNull(word);
    assertEquals(dto.russian(), word.russian());
    assertEquals(dto.english(), word.english());
    assertEquals(dto.spanish(), word.spanish());
    assertEquals(dto.type(), word.type());
    assertNotNull(word.declinationMatrix());

    verify(wordsRepository).create(any(WordEntity.class));
    verify(wordsRepository).save(any(WordEntity.class));
  }

  @Test
  public void shouldThrowExceptionWhenCreatingAWordWithNoConjugationsAndTypeIsVerb() {
    WordEntity wordEntity = WordMother.random().type(WordType.VERB).build();
    when(wordsRepository.create(any(WordEntity.class))).thenReturn(wordEntity);

    CreateWordDto dto = new CreateWordDto(
      wordEntity.russian(),
      wordEntity.english(),
      wordEntity.spanish(),
      wordEntity.type(),
      null,
      null,
      null
    );
    assertThrows(ConjugationsRequired.class, () -> wordsCommandService.handle(dto));
  }

  @Test
  public void shouldThrowExceptionWhenCreatingAWordWithNoDeclinationsAndTypeIsNoun() {
    WordEntity wordEntity = WordMother.random().type(WordType.NOUN).build();
    when(wordsRepository.create(any(WordEntity.class))).thenReturn(wordEntity);

    CreateWordDto dto = new CreateWordDto(
      wordEntity.russian(),
      wordEntity.english(),
      wordEntity.spanish(),
      wordEntity.type(),
      null,
      null,
      null
    );
    assertThrows(DeclinationsRequired.class, () -> wordsCommandService.handle(dto));
  }

  @Test
  public void shouldThrowExceptionWhenCreatingAWordWithNoDeclinationMatrixAndTypeIsAdjectiveOrPronounOrParticipleOrOrdinal() {
    WordEntity wordEntity = WordMother.random().type(WordType.ADJECTIVE).build();
    when(wordsRepository.create(any(WordEntity.class))).thenReturn(wordEntity);

    CreateWordDto dto = new CreateWordDto(
      wordEntity.russian(),
      wordEntity.english(),
      wordEntity.spanish(),
      wordEntity.type(),
      null,
      null,
      null
    );
    assertThrows(DeclinationsRequired.class, () -> wordsCommandService.handle(dto));
  }

}