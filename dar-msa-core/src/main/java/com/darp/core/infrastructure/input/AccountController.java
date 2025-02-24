package com.darp.core.infrastructure.input;

import com.darp.core.application.input.port.AccountService;
import com.darp.core.infrastructure.input.dto.AccountDto;
import com.darp.core.infrastructure.input.dto.CreateAccountDto;
import com.darp.core.infrastructure.input.dto.UpdateAccountDto;
import com.darp.core.infrastructure.input.mapper.AccountDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {
  private final AccountService accountService;
  private final AccountDtoMapper accountMapper;

  @GetMapping("/{id}")
  Mono<AccountDto> findById(@PathVariable String id) {
    log.info("|--> GET /accounts/{} started", id);

    return accountService
        .findById(id)
        .map(accountMapper::toDto)
        .doOnSuccess(account -> log.info("|--> GET /accounts/{} finished successfully", id))
        .doOnError(
            error -> log.error("|--> GET /accounts/{} failed. Reason: {}", id, error.getMessage()));
  }

  @PostMapping
  Mono<AccountDto> save(@RequestBody @Validated CreateAccountDto accountDto) {
    log.info("|--> POST /accounts started");
    return accountService
        .save(accountMapper.toDomain(accountDto))
        .map(accountMapper::toDto)
        .doOnSuccess(savedAccount -> log.info("|--> POST /accounts finished successfully"))
        .doOnError(
            error -> log.error("|--> POST /accounts failed. Reason: {}", error.getMessage()));
  }

  @PutMapping("/{id}")
  Mono<AccountDto> update(
      @PathVariable String id, @RequestBody @Validated UpdateAccountDto accountDto) {
    log.info("|--> PUT /accounts/{} started", id);
    return accountService
        .update(accountMapper.toDomain(id, accountDto))
        .map(accountMapper::toDto)
        .doOnSuccess(updatedAccount -> log.info("|--> PUT /accounts/{} finished successfully", id))
        .doOnError(
            error -> log.error("|--> PUT /accounts/{} failed. Reason: {}", id, error.getMessage()));
  }

  @DeleteMapping("/{id}")
  Mono<Void> delete(@PathVariable String id) {
    log.info("|--> DELETE /accounts/{} started", id);
    return accountService
        .delete(id)
        .doOnSuccess(nothing -> log.info("|--> DELETE /accounts/{} finished successfully", id))
        .doOnError(
            error ->
                log.error("|--> DELETE /accounts/{} failed. Reason: {}", id, error.getMessage()));
  }
}
