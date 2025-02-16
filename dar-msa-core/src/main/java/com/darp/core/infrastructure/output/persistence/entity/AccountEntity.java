package com.darp.core.infrastructure.output.persistence.entity;

import com.darp.core.domain.model.AccountStatus;
import com.darp.core.domain.model.AccountType;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(schema = "public", value = "accounts")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AccountEntity {
  @Id private UUID id;

  private String number;

  private AccountType type;

  private BigDecimal balance;

  private AccountStatus status;

  @NonNull private UUID customerId;
}
