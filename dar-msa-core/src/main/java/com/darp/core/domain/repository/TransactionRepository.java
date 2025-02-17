package com.darp.core.domain.repository;

import com.darp.core.domain.model.Transaction;
import com.darp.core.domain.model.TransactionDetails;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionRepository {
  Mono<Transaction> save(@NotNull @Validated Transaction transaction);

  Flux<TransactionDetails> findByCustomerIdAndExecutedAtBetween(
      String customerId, LocalDate dateFrom, LocalDate dateTo);
}
