package com.darp.core.domain.model;

import java.math.BigDecimal;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class Account {
  private String id;

  private String number;

  private AccountType type;

  private BigDecimal balance;

  private AccountStatus status;

  private String customerId;
}
