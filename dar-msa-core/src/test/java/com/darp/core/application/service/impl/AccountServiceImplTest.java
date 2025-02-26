package com.darp.core.application.service.impl;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.darp.core.domain.exception.NotFoundException;
import com.darp.core.domain.model.Account;
import com.darp.core.domain.model.AccountStatus;
import com.darp.core.domain.model.AccountType;
import com.darp.core.domain.repository.AccountRepository;
import com.darp.core.infrastructure.output.api.CustomersApi;
import com.darp.core.shared.utils.MockData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
  @Mock private AccountRepository accountRepository;
  @Mock private CustomersApi customersApi;

  @InjectMocks private AccountServiceImpl accountService;

  @Test
  void shouldFindById() {
    when(accountRepository.findById(eq("1"))) //
        .thenReturn(Mono.just(MockData.ACCOUNT));

    var result = accountService.findById("1");

    StepVerifier.create(result) //
        .expectNext(MockData.ACCOUNT)
        .verifyComplete();
  }

  @Test
  void shouldThrowExceptionIfNotFoundById() {
    when(accountRepository.findById(anyString())) //
        .thenReturn(Mono.empty());

    var result = accountService.findById("1");

    StepVerifier.create(result) //
        .expectError(NotFoundException.class)
        .verify();
  }

  @Test
  void shouldSaveAccountWhenCustomerExists() {
    var account = MockData.ACCOUNT;
    var expectedAccount = account.toBuilder().status(AccountStatus.ACTIVE).build();

    when(customersApi.findById(anyString())) //
        .thenReturn(Mono.just(MockData.CUSTOMER));
    when(accountRepository.findByNumber(eq(MockData.ACCOUNT_NUMBER))) //
        .thenReturn(Mono.empty());
    when(accountRepository.save(any())) //
        .thenReturn(Mono.just(expectedAccount));

    var result = accountService.save(account);

    StepVerifier.create(result) //
        .assertNext(
            actualAccount ->
                assertThat(actualAccount).usingRecursiveAssertion().isEqualTo(expectedAccount))
        .verifyComplete();

    verify(accountRepository).save(any(Account.class));
  }

  @Test
  void shouldUpdateAccount() {
    var account = MockData.ACCOUNT.toBuilder().build();
    var updatedAccount = account.toBuilder().type(AccountType.CURRENT).build();

    when(accountRepository.findById(eq(account.getId()))) //
        .thenReturn(Mono.just(account));
    when(accountRepository.update(any())) //
        .thenReturn(Mono.just(updatedAccount));

    var result = accountService.update(account);

    StepVerifier.create(result) //
        .assertNext(
            actualAccount ->
                assertThat(actualAccount).usingRecursiveComparison().isEqualTo(updatedAccount))
        .verifyComplete();

    verify(accountRepository).update(any(Account.class));
  }

  @Test
  void shouldDeleteAccount() {
    var account = MockData.ACCOUNT;

    when(accountRepository.findById(eq(account.getId()))) //
        .thenReturn(Mono.just(account));
    when(accountRepository.delete(eq(account))) //
        .thenReturn(Mono.empty());

    var result = accountService.delete(account.getId());

    StepVerifier.create(result) //
        .verifyComplete();

    verify(accountRepository).delete(eq(account));
  }
}
