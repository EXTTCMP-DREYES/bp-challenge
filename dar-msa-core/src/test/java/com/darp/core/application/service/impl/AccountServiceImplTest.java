package com.darp.core.application.service.impl;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import com.darp.core.domain.exception.NotFoundException;
import com.darp.core.domain.model.Account;
import com.darp.core.domain.model.AccountStatus;
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
    var account = MockData.ACCOUNT.toBuilder().customerId("1").build();
    var expectedAccount = account.toBuilder().status(AccountStatus.ACTIVE).build();

    when(customersApi.findById(anyString())) //
        .thenReturn(Mono.just(MockData.CUSTOMER));
    when(accountRepository.findByCustomerAndNumber(any(), any())) //
        .thenReturn(Mono.empty());
    when(accountRepository.save(any(Account.class))) //
        .thenReturn(Mono.just(expectedAccount));

    var result = accountService.save(account);

    StepVerifier.create(result) //
        .assertNext(
            actualAccount ->
                assertThat(actualAccount).usingRecursiveAssertion().isEqualTo(expectedAccount))
        .verifyComplete();
  }

  @Test
  void shouldThrowExceptionWhenCustomerNotFound() {
    var account = MockData.ACCOUNT;

    when(customersApi.findById(anyString())) //
        .thenReturn(Mono.error(new NotFoundException("")));

    var result = accountService.save(account);

    StepVerifier.create(result) //
        .expectError(NotFoundException.class)
        .verify();
  }

  @Test
  void shouldLogErrorWhenSaveFails() {
    var account = MockData.ACCOUNT;
    var storedAccount = account.toBuilder().status(AccountStatus.ACTIVE).build();
    var customer = MockData.CUSTOMER;

    when(customersApi.findById(anyString())) //
        .thenReturn(Mono.just(customer));
    when(accountRepository.save(storedAccount))
        .thenReturn(Mono.error(new RuntimeException("Save failed")));

    var result = accountService.save(account);

    StepVerifier.create(result) //
        .expectError(RuntimeException.class)
        .verify();
  }
}
