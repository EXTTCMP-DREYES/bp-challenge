package com.darp.core.application.input.port;

import com.darp.core.application.dto.TransactionFilterParams;
import com.darp.core.domain.model.Transaction;
import com.darp.core.domain.model.TransactionDetails;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {
  Mono<Transaction> deposit(@NotNull @Validated Transaction transaction);

  Mono<Transaction> withdrawal(@NotNull @Validated Transaction transaction);

  Flux<TransactionDetails> getReport(TransactionFilterParams filterParams);
}
