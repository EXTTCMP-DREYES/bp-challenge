package com.darp.core.infrastructure.output.persistence;

import com.darp.core.infrastructure.output.persistence.entity.TransactionEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface TransactionReactiveRepository
    extends ReactiveCrudRepository<TransactionEntity, String> {
  Flux<TransactionEntity> findAllByAccountId(String accountId);
}
