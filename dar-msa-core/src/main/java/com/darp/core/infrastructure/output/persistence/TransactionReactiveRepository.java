package com.darp.core.infrastructure.output.persistence;

import com.darp.core.infrastructure.output.entity.TransactionEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TransactionReactiveRepository
    extends ReactiveCrudRepository<TransactionEntity, String> {}
