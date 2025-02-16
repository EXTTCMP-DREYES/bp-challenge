package com.darp.core.application.service.impl;

import com.darp.core.application.input.port.AccountService;
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
        .doOnError(error -> log.error("|--> Error searching account by id: {}", error.getMessage()));
  }

  @Override
  public Mono<Account> save(@NonNull Account account) {
    var storedAccount = account.toBuilder().status(AccountStatus.ACTIVE).build();

    return customersApi
        .findById(account.getCustomerId())
        .flatMap(
            customer -> {
              log.info("|--> Customer found: {}", customer.getFullName());
              log.info("|--> Save account started: {}", account.getNumber());

              return accountRepository.save(storedAccount);
            })
        .doOnSuccess(savedAccount -> log.info("|--> Account saved: {}", savedAccount.getNumber()))
        .doOnError(error -> log.error("|--> Error saving account: {}", error.getMessage()));
  }
}
