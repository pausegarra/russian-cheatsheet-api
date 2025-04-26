package es.pausegarra.russian_cheatsheet.vocabulary.application.services.find_paginated_words;

import es.pausegarra.russian_cheatsheet.common.application.interfaces.Service;
import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.criterias.WordsSearchCriteria;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.repositories.WordsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class FindAllWordsPaginatedService implements Service<FindAllWordsPaginatedDto, PaginatedDto<WordDto>> {

  private final WordsRepository wordsRepository;

  @Override
  public PaginatedDto<WordDto> handle(FindAllWordsPaginatedDto dto) {
    WordsSearchCriteria criteria = WordsSearchCriteria.create(
      dto.page(), dto.pageSize(), dto.sortBy(), dto.sortDirection(), dto.search());
    Paginated<WordEntity> words = wordsRepository.findByCriteria(criteria);

    List<WordDto> wordsDto = words.data()
      .stream()
      .map(WordDto::fromEntity)
      .toList();

    return PaginatedDto.fromPaginated(words, wordsDto);
  }

}
