package com.darp.core.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Validated
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@ToString
public class TransactionDetails {
  private String id;

  private LocalDate executedAt;

  private TransactionType type;

  private BigDecimal amount;

  private BigDecimal balance;

  private String accountId;

  private Customer customer;

  private Account account;
}
