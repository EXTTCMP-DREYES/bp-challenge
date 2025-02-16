package com.darp.core.domain.repository;

import com.darp.core.domain.model.Transaction;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

public interface TransactionRepository {
  Mono<Transaction> save(@NotNull @Validated Transaction transaction);
}
