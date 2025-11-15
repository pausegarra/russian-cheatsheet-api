package es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest_clients.memrise;

import com.fasterxml.jackson.annotation.JsonProperty;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.cron.MemriseWordDto;

import java.util.List;

public record ListWordsResponse(
  List<MemriseWordDto> learnables,

  @JsonProperty("next_page_token") String nextPageToken
) {}
