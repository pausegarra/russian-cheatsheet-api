package es.pausegarra.russkiy_po_moyemu.vocabulary.infrastructure.rest;

import es.pausegarra.russkiy_po_moyemu.common.domain.audit.AuditFields;
import es.pausegarra.russkiy_po_moyemu.vocabulary.application.services.create_word.CreateWordCommand;
import es.pausegarra.russkiy_po_moyemu.vocabulary.application.services.create_word.CreateWordService;
import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.entities.WordEntity;
import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.enums.WordTypes;
import es.pausegarra.russkiy_po_moyemu.vocabulary.infrastructure.requests.CreateWordRequest;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
class CreateWordResourceTest {

  @InjectMock
  CreateWordService createWordService;

  @Inject
  CreateWordResource createWordResource;

  @Test
  @TestSecurity(authorizationEnabled = false)
  public void shouldCreateWord() {
    WordEntity word = new WordEntity(
        UUID.randomUUID(),
        "a",
        "a",
        "a",
        WordTypes.VERB,
        new AuditFields(Instant.now(), Instant.now())
    );

    when(createWordService.handle(any(CreateWordCommand.class)))
        .thenReturn(word.getId());

    CreateWordRequest request = new CreateWordRequest(
        "a",
        "a",
        "a",
        "VERB"
    );
    Response response = createWordResource.createWord(request);

    assertEquals(201, response.getStatus());

    verify(createWordService, times(1)).handle(any(CreateWordCommand.class));
  }

}