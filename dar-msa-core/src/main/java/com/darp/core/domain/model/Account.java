package com.darp.core.domain.model;

import java.math.BigDecimal;
import lombok.*;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class Account {
  @Id private String id;

  private String number;

  private AccountType type;

  private BigDecimal balance;

  private AccountStatus status;

  private String customerId;
}
