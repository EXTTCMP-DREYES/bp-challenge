package com.darp.core.infrastructure.output.persistence.impl;

import com.darp.core.domain.model.Transaction;
import com.darp.core.domain.repository.TransactionRepository;
import com.darp.core.infrastructure.output.persistence.TransactionReactiveRepository;
import com.darp.core.infrastructure.output.persistence.mapper.TransactionEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TransactionRepositoryImpl implements TransactionRepository {
  private final TransactionReactiveRepository transactionRepository;
  private final TransactionEntityMapper transactionMapper;

  @Override
  public Mono<Transaction> save(Transaction transaction) {
    var entity = transactionMapper.toEntity(transaction);
    return transactionRepository.save(entity).map(transactionMapper::toDomain);
  }
}
