package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.interfaces.Service;
import es.pausegarra.russian_cheatsheet.vocabulary.application.services.delete_word.DeleteWordDto;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@QuarkusTest
class DeleteWordResourceTest {

  @InjectMock
  Service<DeleteWordDto, Void> service;

  @Inject
  DeleteWordResource deleteWordResource;

  @Test
  @TestSecurity(authorizationEnabled = false)
  public void shouldDeleteWord() {
    doNothing().when(service)
      .handle(any(DeleteWordDto.class));

    Response response = deleteWordResource.deleteWord(UUID.randomUUID()
                                                        .toString());

    assertNotNull(response);
    assertEquals(204, response.getStatus());
  }

}