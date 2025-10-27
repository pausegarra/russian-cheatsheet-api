package es.pausegarra.russian_cheatsheet.context.words.application.use_cases;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.requests.create_word.CreateWordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.responses.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.services.WordsCommandService;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class CreateWordUseCase implements UseCase<CreateWordDto, WordDto> {

  private final WordsCommandService wordsCommandService;

  @Override
  public WordDto handle(CreateWordDto dto) {
    WordEntity wordEntity = wordsCommandService.create(dto);

    return WordDto.fromEntity(wordEntity);
  }

}
