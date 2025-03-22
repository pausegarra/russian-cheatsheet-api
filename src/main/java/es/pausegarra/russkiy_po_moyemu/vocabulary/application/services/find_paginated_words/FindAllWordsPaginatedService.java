package es.pausegarra.russkiy_po_moyemu.vocabulary.application.services.find_paginated_words;

import es.pausegarra.russkiy_po_moyemu.common.application.interfaces.Service;
import es.pausegarra.russkiy_po_moyemu.common.application.pagination.Paginated;
import es.pausegarra.russkiy_po_moyemu.common.application.pagination.PaginatedDto;
import es.pausegarra.russkiy_po_moyemu.vocabulary.application.dto.WordDto;
import es.pausegarra.russkiy_po_moyemu.vocabulary.application.mappers.WordMapper;
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

  private final WordMapper wordMapper;

  @Override
  public PaginatedDto<WordDto> handle(FindAllWordsPaginatedQuery dto) {
    WordsSearchCriteria criteria = WordsSearchCriteria.create(dto.page(), dto.pageSize(), "spanish", "asc");
    Paginated<WordEntity> words = wordsRepository.findByCriteria(criteria);

    List<WordDto> wordsDto = words.data()
        .stream()
        .map(wordMapper::fromEntityToDto)
        .toList();

    return PaginatedDto.fromPaginated(words, wordsDto);
  }

}
