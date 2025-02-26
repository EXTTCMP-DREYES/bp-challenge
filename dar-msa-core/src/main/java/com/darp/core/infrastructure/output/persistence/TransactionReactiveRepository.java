package com.darp.core.infrastructure.output.persistence;

import com.darp.core.infrastructure.output.persistence.entity.TransactionEntity;
import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TransactionReactiveRepository
    extends ReactiveCrudRepository<TransactionEntity, UUID> {}
