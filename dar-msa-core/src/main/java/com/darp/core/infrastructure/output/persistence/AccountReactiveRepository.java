package com.darp.core.infrastructure.output.persistence;

import com.darp.core.infrastructure.output.entity.AccountEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AccountReactiveRepository extends ReactiveCrudRepository<AccountEntity, String> {}
