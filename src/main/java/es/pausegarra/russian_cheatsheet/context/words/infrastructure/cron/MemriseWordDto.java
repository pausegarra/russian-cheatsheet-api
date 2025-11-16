package es.pausegarra.russian_cheatsheet.context.words.infrastructure.cron;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MemriseWordDto(

  @JsonProperty("source_value") String spanish,

  @JsonProperty("target_value") String russian

) {}
