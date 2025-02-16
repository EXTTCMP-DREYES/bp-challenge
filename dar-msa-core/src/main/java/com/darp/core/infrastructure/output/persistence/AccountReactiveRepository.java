package com.darp.core.infrastructure.output.persistence;

import com.darp.core.infrastructure.output.persistence.entity.AccountEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface AccountReactiveRepository extends ReactiveCrudRepository<AccountEntity, String> {
  Flux<AccountEntity> findByCustomerId(String customerId);
}
