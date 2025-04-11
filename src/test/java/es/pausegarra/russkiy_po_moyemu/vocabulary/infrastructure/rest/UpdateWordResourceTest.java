package es.pausegarra.russkiy_po_moyemu.vocabulary.infrastructure.rest;

import es.pausegarra.russkiy_po_moyemu.common.application.interfaces.Service;
import es.pausegarra.russkiy_po_moyemu.vocabulary.application.services.update_word.UpdateWordDto;
import es.pausegarra.russkiy_po_moyemu.vocabulary.infrastructure.requests.UpdateWordRequest;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@QuarkusTest
class UpdateWordResourceTest {

  @InjectMock
  Service<UpdateWordDto, Void> updateWordService;

  @Inject
  UpdateWordResource updateWordResource;

  @Test
  @TestSecurity(authorizationEnabled = false)
  public void shouldUpdateWord() {
    doNothing().when(updateWordService)
      .handle(any(UpdateWordDto.class));

    UpdateWordRequest request = new UpdateWordRequest("word", "english", "spanish", "VERB");
    updateWordResource.updateWord(
      UUID.randomUUID()
        .toString(), request
    );

    verify(updateWordService).handle(any(UpdateWordDto.class));
  }

}