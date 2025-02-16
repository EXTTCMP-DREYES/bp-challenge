package com.darp.core.infrastructure.input;

import com.darp.core.application.input.port.AccountService;
import com.darp.core.infrastructure.input.dto.AccountDto;
import com.darp.core.infrastructure.input.dto.CreateAccountDto;
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
    log.info("|--> Searching account by id: {}", id);

    return accountService.findById(id).map(accountMapper::toDto);
  }

  @PostMapping
  Mono<AccountDto> save(@RequestBody @Validated CreateAccountDto accountDto) {
    log.info("|--> Save account started: {}", accountDto);
    return accountService.save(accountMapper.toDomain(accountDto)).map(accountMapper::toDto);
  }
}
