package es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest_clients.memrise;

import io.quarkus.rest.client.reactive.ClientQueryParam;
import io.quarkus.rest.client.reactive.ClientQueryParams;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(
  configKey = "memrise"
)
public interface MemriseRestClient {

  @GET
  @Path("/v1.25/me/language_pairs/183/learnables/")
  @ClientQueryParams({
    @ClientQueryParam(name = "learning_state", value = "${app.memrise.learning-state}"),
    @ClientQueryParam(name = "ordering", value = "${app.memrise.ordering}"),
    @ClientQueryParam(name = "ordering_direction", value = "${app.memrise.ordering-direction}"),
    @ClientQueryParam(name = "page_size", value = "${app.memrise.page-size}")
  })
  @ClientHeaderParam(
    name = "Authorization",
    value = "Bearer ${app.memrise.access-token}"
  )
  ListWordsResponse getWords(
    @QueryParam("next_page_token")
    String nextPageToken
  );

}
