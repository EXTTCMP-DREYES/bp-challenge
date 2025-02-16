package com.darp.core.application.input.port;

import com.darp.core.domain.model.Transaction;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

public interface TransactionService {
  Mono<Transaction> deposit(@NotNull @Validated Transaction transaction);

  Mono<Transaction> withdrawal(@NotNull @Validated Transaction transaction);
}
