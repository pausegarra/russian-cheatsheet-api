package es.pausegarra.russkiy_po_moyemu.vocabulary.application.services.find_paginated_words;

import es.pausegarra.russkiy_po_moyemu.common.application.interfaces.Service;
import es.pausegarra.russkiy_po_moyemu.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.russkiy_po_moyemu.common.application.pagination.PaginatedDto;
import es.pausegarra.russkiy_po_moyemu.vocabulary.application.dto.WordDto;
import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.criterias.WordsSearchCriteria;
import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.entities.WordEntity;
import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.repositories.WordsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class FindAllWordsPaginatedService implements Service<FindAllWordsPaginatedQuery, PaginatedDto<WordDto>> {

  private final WordsRepository wordsRepository;

  @Override
  public PaginatedDto<WordDto> handle(FindAllWordsPaginatedQuery dto) {
    WordsSearchCriteria criteria = WordsSearchCriteria.create(dto.page(), dto.pageSize(), dto.sortBy(), dto.sortDirection());
    Paginated<WordEntity> words = wordsRepository.findByCriteria(criteria);

    List<WordDto> wordsDto = words.data()
        .stream()
        .map(WordDto::fromEntity)
        .toList();

    return PaginatedDto.fromPaginated(words, wordsDto);
  }

}
