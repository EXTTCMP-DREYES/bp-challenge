package com.darp.customers.infrastructure.output.persistence.impl;

import com.darp.customers.domain.model.ReportGenerationEvent;
import com.darp.customers.domain.repository.ReportEventRepository;
import com.darp.customers.infrastructure.output.persistence.ReportEventReactiveRepository;
import com.darp.customers.infrastructure.output.persistence.mapper.ReportEventEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ReportEventRepositoryImpl implements ReportEventRepository {
  private final ReportEventReactiveRepository repository;
  private final ReportEventEntityMapper mapper;

  @Override
  public Mono<ReportGenerationEvent> save(ReportGenerationEvent event) {
    var entity = mapper.toEntity(event);
    return repository.save(entity).map(mapper::toDomain);
  }
}
