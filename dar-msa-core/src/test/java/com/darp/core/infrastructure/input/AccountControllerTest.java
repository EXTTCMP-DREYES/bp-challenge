package com.darp.core.infrastructure.input;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.darp.core.application.input.port.AccountService;
import com.darp.core.domain.exception.NotFoundException;
import com.darp.core.domain.model.Account;
import com.darp.core.infrastructure.input.mapper.AccountDtoMapper;
import com.darp.core.infrastructure.output.persistence.AccountReactiveRepository;
import com.darp.core.infrastructure.output.persistence.TransactionReactiveRepository;
import com.darp.core.shared.utils.MockData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = AccountController.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class AccountControllerTest {
  @MockitoBean private AccountReactiveRepository accountReactiveRepository;
  @MockitoBean private TransactionReactiveRepository transactionReactiveRepository;
  @MockitoBean private AccountService accountService;

  @Autowired private WebTestClient testClient;

  @Test
  void shouldFindAccountById() {
    var accountId = "b00ff9a0-8749-4004-bb6f-c074756a6691";

    when(accountService.findById(eq(accountId))).thenReturn(Mono.just(MockData.ACCOUNT));

    testClient
        .get()
        .uri("/accounts/{id}", accountId)
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.OK)
        .expectBody()
        .json(
            "{\"id\":\"b00ff9a0-8749-4004-bb6f-c074756a6691\",\"number\":\"123456\",\"type\":\"SAVINGS\",\"balance\":10,\"status\":\"ACTIVE\",\"customerId\":\"c3996159-662e-486d-b88f-a40ac3fddcc4\"}");
  }

  @Test
  void shouldReturnNotFoundWhenAccountDoesNotExist() {
    var accountId = "non-existent-id";

    when(accountService.findById(eq(accountId))).thenReturn(Mono.error(new NotFoundException("")));

    testClient
        .get()
        .uri("/accounts/{id}", accountId)
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  void shouldSaveAccountSuccessfully() {
    var createAccountDto = MockData.CREATE_ACCOUNT_DTO;
    var account = MockData.ACCOUNT;

    when(accountService.save(any(Account.class))).thenReturn(Mono.just(account));

    testClient
        .post()
        .uri("/accounts")
        .bodyValue(createAccountDto)
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.OK)
        .expectBody()
        .json(
            "{\"id\":\"b00ff9a0-8749-4004-bb6f-c074756a6691\",\"number\":\"123456\",\"type\":\"SAVINGS\",\"balance\":10,\"status\":\"ACTIVE\",\"customerId\":\"c3996159-662e-486d-b88f-a40ac3fddcc4\"}");
  }

  @Test
  void shouldReturnBadRequestWhenSavingInvalidAccount() {
    var createAccountDto = MockData.CREATE_ACCOUNT_DTO.toBuilder().number("invalid").build();

    testClient
        .post()
        .uri("/accounts")
        .bodyValue(createAccountDto)
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void shouldUpdateAccountSuccessfully() {
    var accountId = "b00ff9a0-8749-4004-bb6f-c074756a6691";
    var updateAccountDto = MockData.UPDATE_ACCOUNT_DTO;

    when(accountService.update(any(Account.class))).thenReturn(Mono.just(MockData.ACCOUNT));

    testClient
        .patch()
        .uri("/accounts/{id}", accountId)
        .bodyValue(updateAccountDto)
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.OK)
        .expectBody()
        .json(
            "{\"id\":\"b00ff9a0-8749-4004-bb6f-c074756a6691\",\"number\":\"123456\",\"type\":\"SAVINGS\",\"balance\":10,\"status\":\"ACTIVE\",\"customerId\":\"c3996159-662e-486d-b88f-a40ac3fddcc4\"}");
  }

  @Test
  void shouldReturnBadRequestWhenUpdatingInvalidAccount() {
    var accountId = "b00ff9a0-8749-4004-bb6f-c074756a6691";
    var updateAccountDto = MockData.UPDATE_ACCOUNT_DTO.toBuilder().type("invalid").build();

    testClient
        .patch()
        .uri("/accounts/{id}", accountId)
        .bodyValue(updateAccountDto)
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void shouldDeleteAccountSuccessfully() {
    var accountId = "b00ff9a0-8749-4004-bb6f-c074756a6691";

    when(accountService.delete(eq(accountId))) //
        .thenReturn(Mono.empty());

    testClient
        .delete()
        .uri("/accounts/{id}", accountId)
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.OK);
  }

  @TestConfiguration
  public static class TestConfig {
    @Bean
    @SuppressWarnings("all")
    public AccountDtoMapper accountDtoMapper() {
      return Mappers.getMapper(AccountDtoMapper.class);
    }
  }
}
