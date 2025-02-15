package com.darp.core.infrastructure.output.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(schema = "public", value = "transactions")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TransactionEntity {
  @Id private UUID id;

  private LocalDate executedAt;

  private TransactionType type;

  private BigDecimal amount;

  private BigDecimal balance;

  @NonNull private UUID accountId;
}
