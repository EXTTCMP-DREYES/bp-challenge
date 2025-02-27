package com.darp.core.infrastructure.output.persistence;

import com.darp.core.domain.model.AccountStatus;
import com.darp.core.infrastructure.output.persistence.entity.AccountEntity;
import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface AccountReactiveRepository extends ReactiveCrudRepository<AccountEntity, UUID> {
  Mono<AccountEntity> findByIdAndStatus(UUID id, AccountStatus status);

  Mono<Boolean> existsByNumber(String number);
}
