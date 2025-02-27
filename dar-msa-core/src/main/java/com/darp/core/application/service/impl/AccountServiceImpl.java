package com.darp.core.application.service.impl;

import com.darp.core.application.input.port.AccountService;
import com.darp.core.domain.exception.DuplicateAccountException;
import com.darp.core.domain.exception.NotFoundException;
import com.darp.core.domain.model.Account;
import com.darp.core.domain.model.AccountStatus;
import com.darp.core.domain.repository.AccountRepository;
import com.darp.core.infrastructure.output.api.CustomersApi;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
  private final AccountRepository accountRepository;
  private final CustomersApi customersApi;

  @Override
  public Mono<Account> findById(@NonNull @NotBlank String id) {
    log.info("|--> Searching account by id: {}", id);

    return accountRepository
        .findById(id)
        .switchIfEmpty(Mono.error(new NotFoundException("Account not found")))
        .doOnSuccess(account -> log.info("|--> Account found: {}", account.getNumber()))
        .doOnError(
            error -> log.error("|--> Error searching account by id: {}", error.getMessage()));
  }

  @Override
  public Mono<Account> save(@NonNull Account account) {
    log.info("|--> Save account started: {}", account.getNumber());

    var newAccount = account.toBuilder().status(AccountStatus.ACTIVE).build();

    return customersApi
        .findById(account.getCustomerId())
        .flatMap(customer -> accountRepository.existsByNumber(account.getNumber()))
        .flatMap(
            isAccountNumberTaken -> {
              if (isAccountNumberTaken) {
                return Mono.error(new DuplicateAccountException("Account already exists"));
              }

              return accountRepository.save(newAccount);
            })
        .doOnSuccess(unused -> log.info("|--> Account saved: {}", newAccount.getNumber()))
        .doOnError(error -> log.error("|--> Error saving account: {}", error.getMessage()));
  }

  @Override
  public Mono<Account> update(@NonNull Account account) {
    log.info("|--> Update account started: {}", account.getId());

    return findById(account.getId())
        .flatMap(
            storedAccount -> {
              var updatedAccount = storedAccount.toBuilder().type(account.getType()).build();

              return accountRepository.update(updatedAccount);
            })
        .doOnSuccess(
            updatedAccount -> log.info("|--> Account updated: {}", updatedAccount.getNumber()))
        .doOnError(error -> log.error("|--> Error updating account: {}", error.getMessage()));
  }

  @Override
  public Mono<Void> delete(@NonNull String id) {
    log.info("|--> Delete account started: {}", id);

    return findById(id)
        .flatMap(accountRepository::delete)
        .doOnSuccess(nothing -> log.info("|--> Account deleted: {}", id))
        .doOnError(error -> log.error("|--> Error deleting account: {}", error.getMessage()));
  }
}
