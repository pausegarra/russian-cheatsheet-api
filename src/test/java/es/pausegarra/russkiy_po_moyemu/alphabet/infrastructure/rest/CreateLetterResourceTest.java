package es.pausegarra.russkiy_po_moyemu.alphabet.infrastructure.rest;

import es.pausegarra.russkiy_po_moyemu.alphabet.application.services.create_letter.CreateLetterCommand;
import es.pausegarra.russkiy_po_moyemu.alphabet.application.services.create_letter.CreateLetterService;
import es.pausegarra.russkiy_po_moyemu.alphabet.infrastructure.requests.CreateLetterRequest;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@QuarkusTest
class CreateLetterResourceTest {

  @InjectMock
  CreateLetterService createLetterService;

  @Inject
  CreateLetterResource createLetterResource;

  @Test
  @TestSecurity(authorizationEnabled = false)
  public void shouldCreateLetter() {
    CreateLetterRequest request = new CreateLetterRequest("a", "a", "a");
    when(createLetterService.handle(any(CreateLetterCommand.class))).thenReturn(UUID.randomUUID());

    Response response = createLetterResource.createLetter(request);

    assertEquals(201, response.getStatus());

    verify(createLetterService, times(1)).handle(any(CreateLetterCommand.class));
  }

}