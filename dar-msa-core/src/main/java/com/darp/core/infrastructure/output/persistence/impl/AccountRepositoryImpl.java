package com.darp.core.infrastructure.output.persistence.impl;

import com.darp.core.domain.model.Account;
import com.darp.core.domain.repository.AccountRepository;
import com.darp.core.infrastructure.output.persistence.AccountReactiveRepository;
import com.darp.core.infrastructure.output.persistence.mapper.AccountEntityMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
@Valid
public class AccountRepositoryImpl implements AccountRepository {
  private final AccountReactiveRepository accountReactiveRepository;
  private final AccountEntityMapper accountMapper;

  @Override
  public Mono<Account> findById(@NonNull @NotBlank String id) {
    return accountReactiveRepository.findById(id).map(accountMapper::toDomain);
  }

  @Override
  public Mono<Account> findByCustomerAndNumber(
      @NonNull @NotBlank String customerId, @NonNull @NotBlank String number) {
    return accountReactiveRepository
        .findByCustomerIdAndNumber(UUID.fromString(customerId), number)
        .map(accountMapper::toDomain);
  }

  @Override
  public Mono<Account> save(@NonNull Account account) {
    var entity = accountMapper.toEntity(account);
    return accountReactiveRepository.save(entity).map(accountMapper::toDomain);
  }

  @Override
  public Mono<Account> update(@NonNull Account account) {
    return accountReactiveRepository
        .save(accountMapper.toEntity(account))
        .map(accountMapper::toDomain);
  }

  @Override
  public Mono<Void> delete(@NonNull String id) {
    return accountReactiveRepository.deleteById(id);
  }
}
