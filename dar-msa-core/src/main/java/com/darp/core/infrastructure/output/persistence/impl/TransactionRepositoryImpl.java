package com.darp.core.infrastructure.output.persistence.impl;

import com.darp.core.domain.model.Transaction;
import com.darp.core.domain.model.TransactionDetails;
import com.darp.core.domain.repository.TransactionRepository;
import com.darp.core.infrastructure.output.persistence.TransactionReactiveRepository;
import com.darp.core.infrastructure.output.persistence.mapper.TransactionEntityMapper;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TransactionRepositoryImpl implements TransactionRepository {
  private final TransactionReactiveRepository transactionRepository;
  private final R2dbcEntityTemplate template;
  private final TransactionEntityMapper transactionMapper;

  @Override
  public Mono<Transaction> save(Transaction transaction) {
    var entity = transactionMapper.toEntity(transaction);
    return transactionRepository.save(entity).map(transactionMapper::toDomain);
  }

  @Override
  public Flux<TransactionDetails> findByCustomerIdAndExecutedAtBetween(
      String customerId, LocalDate dateFrom, LocalDate dateTo) {
    var sql =
        """
        select
          t.id as transaction_id,
          t.executed_at,
          t.type as transaction_type,
          t.amount,
          t.balance as final_balance,
          t.account_id,
          a.number as account_number,
          a.type as account_type,
          a.balance as account_balance
        from
          public.transactions t
        join public.accounts a on a.id = t.account_id
        where
          a.customer_id = :customerId
            and t.executed_at between :dateFrom and :dateTo""";

    return template
        .getDatabaseClient()
        .sql(sql)
        .bind("customerId", UUID.fromString(customerId))
        .bind("dateFrom", dateFrom.atStartOfDay())
        .bind("dateTo", dateTo.atStartOfDay())
        .map((row, rowMetadata) -> transactionMapper.toDomainDetails(row))
        .all();
  }
}
