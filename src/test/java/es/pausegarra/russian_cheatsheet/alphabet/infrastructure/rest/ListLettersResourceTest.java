package es.pausegarra.russian_cheatsheet.alphabet.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.alphabet.application.dtos.LetterDto;
import es.pausegarra.russian_cheatsheet.alphabet.application.services.list_letters.ListLettersDto;
import es.pausegarra.russian_cheatsheet.alphabet.application.services.list_letters.ListLettersService;
import es.pausegarra.russian_cheatsheet.alphabet.domain.entities.LetterEntity;
import es.pausegarra.russian_cheatsheet.alphabet.infrastructure.projections.LetterPresentation;
import es.pausegarra.russian_cheatsheet.common.domain.audit.AuditFields;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@QuarkusTest
class ListLettersResourceTest {

  @InjectMock
  ListLettersService listLettersService;

  @Inject
  ListLettersResource listLettersResource;

  @Test
  public void shouldReturnAllLetters() {
    LetterEntity letter = new LetterEntity(
      UUID.randomUUID(), "a", "a", "a", new AuditFields(Instant.now(), Instant.now()));
    LetterDto letterDto = LetterDto.fromEntity(letter);

    when(listLettersService.handle(any(ListLettersDto.class))).thenReturn(List.of(letterDto));

    RestResponse<List<LetterPresentation>> response = listLettersResource.listLetters();

    assertEquals(200, response.getStatus());

    verify(listLettersService, times(1)).handle(any(ListLettersDto.class));
  }

}