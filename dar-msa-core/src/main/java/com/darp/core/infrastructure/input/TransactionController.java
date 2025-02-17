package com.darp.core.infrastructure.input;

import com.darp.core.application.dto.TransactionFilterParams;
import com.darp.core.application.input.port.TransactionService;
import com.darp.core.infrastructure.input.dto.TransactionDetailsDto;
import com.darp.core.infrastructure.input.mapper.TransactionDtoMapper;
import com.darp.core.infrastructure.output.api.dto.CreateTransactionDto;
import com.darp.core.infrastructure.output.api.dto.TransactionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {
  private final TransactionService transactionService;
  private final TransactionDtoMapper transactionMapper;

  @PostMapping
  public Mono<TransactionDto> create(@Validated @RequestBody CreateTransactionDto transactionDto) {
    log.info("|--> POST /transactions Started.");

    var transaction = transactionMapper.toDomain(transactionDto);
    var response =
        transactionDto.isDeposit()
            ? transactionService.deposit(transaction)
            : transactionService.withdrawal(transaction);

    return response
        .map(transactionMapper::toDto)
        .doOnSuccess(dto -> log.info("|--> POST /transactions Ended successfully"));
  }

  @GetMapping("/report")
  public Flux<TransactionDetailsDto> generateReport(@Validated TransactionFilterParams params) {
    log.info("|--> GET /transactions/report Started.");

    return transactionService
        .getReport(params)
        .map(transactionMapper::toDetailsDto)
        .doOnComplete(() -> log.info("|--> GET /transactions/report Ended successfully"));
  }
}
