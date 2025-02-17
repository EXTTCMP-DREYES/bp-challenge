package com.darp.core.application.service.impl;

import com.darp.core.application.dto.TransactionFilterParams;
import com.darp.core.application.input.port.TransactionService;
import com.darp.core.domain.exception.InsufficientFoundsException;
import com.darp.core.domain.exception.NotFoundException;
import com.darp.core.domain.exception.TransactionsReportException;
import com.darp.core.domain.model.Transaction;
import com.darp.core.domain.model.TransactionDetails;
import com.darp.core.domain.model.TransactionType;
import com.darp.core.domain.repository.AccountRepository;
import com.darp.core.domain.repository.TransactionRepository;
import com.darp.core.infrastructure.output.api.CustomersApi;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
  private final TransactionRepository transactionRepository;
  private final AccountRepository accountRepository;
  private final CustomersApi customersApi;

  @Override
  public Mono<Transaction> deposit(Transaction transaction) {
    log.info("|--> Deposit started for account: [{}]", transaction.getAccountId());

    return accountRepository
        .findById(transaction.getAccountId())
        .switchIfEmpty(Mono.error(new NotFoundException("Account not found")))
        .flatMap(
            account -> {
              var previousBalance = account.getBalance();
              var actualBalance = previousBalance.add(transaction.getAmount());

              account.setBalance(actualBalance);
              var savedTransaction =
                  transaction.toBuilder()
                      .balance(actualBalance)
                      .type(TransactionType.DEPOSIT)
                      .executedAt(LocalDate.now()) // Inject clock for test purposes
                      .build();

              return accountRepository
                  .save(account)
                  .then(transactionRepository.save(savedTransaction));
            })
        .doOnSuccess(
            savedTransaction ->
                log.info("|--> Deposit saved successfully: [{}]", savedTransaction.getId()))
        .doOnError(error -> log.error("|--> Error saving deposit: [{}]", error.getMessage()));
  }

  @Override
  public Mono<Transaction> withdrawal(Transaction transaction) {
    log.info("|--> Withdrawal started for account: [{}]", transaction.getAccountId());

    return accountRepository
        .findById(transaction.getAccountId())
        .switchIfEmpty(Mono.error(new NotFoundException("Account not found")))
        .flatMap(
            account -> {
              var previousBalance = account.getBalance();
              // For withdrawal, transaction amount should be negative
              var actualBalance = previousBalance.add(transaction.getAmount());

              var hasInsufficientFounds = actualBalance.compareTo(BigDecimal.ZERO) < 0;
              if (hasInsufficientFounds) {
                return Mono.error(new InsufficientFoundsException("Insufficient balance"));
              }

              account.setBalance(actualBalance);
              var savedTransaction =
                  transaction.toBuilder()
                      .balance(actualBalance)
                      .type(TransactionType.WITHDRAWAL)
                      .executedAt(LocalDate.now()) // Inject clock for test purposes
                      .build();

              return accountRepository
                  .save(account)
                  .then(transactionRepository.save(savedTransaction));
            })
        .doOnSuccess(
            savedTransaction ->
                log.info("|--> Withdrawal saved successfully: [{}]", savedTransaction.getId()))
        .doOnError(error -> log.error("|--> Error saving withdrawal: [{}]", error.getMessage()));
  }

  @Override
  public Flux<TransactionDetails> getReport(TransactionFilterParams filterParams) {
    log.info("|--> Generating report for customer: [{}]", filterParams.customerId());

    var dateFrom = filterParams.dateRange().get(0);
    var dateTo = filterParams.dateRange().get(1);

    if (dateFrom.isAfter(dateTo)) {
      log.error("|--> Invalid date range: {}", filterParams.dateRange());
      return Flux.error(new TransactionsReportException("Invalid date range"));
    }

    return customersApi
        .findById(filterParams.customerId())
        .doOnSuccess(
            customer ->
                log.info("|--> Customer found: [{}]. Generating report...", customer.getId()))
        .doOnError(error -> log.error("|--> Error finding customer: [{}]", error.getMessage()))
        .flatMapMany(
            customer ->
                transactionRepository
                    .findByCustomerIdAndExecutedAtBetween(customer.getId(), dateFrom, dateTo)
                    .map(details -> details.toBuilder().customer(customer).build()))
        .doOnComplete(() -> log.info("|--> Report generated successfully"))
        .doOnError(error -> log.error("|--> Error generating report: [{}]", error.getMessage()));
  }
}
