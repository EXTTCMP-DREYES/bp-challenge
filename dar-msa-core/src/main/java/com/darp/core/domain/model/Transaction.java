package com.darp.core.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class Transaction {
  @NotBlank
  @Pattern(
      regexp =
          "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[1-5][a-fA-F0-9]{3}-[89abAB][a-fA-F0-9]{3}-[a-fA-F0-9]{12}$",
      message = "Must be a valid UUID")
  private String id;

  private LocalDate executedAt;

  private TransactionType type;

  @NotNull private BigDecimal amount;

  @NotNull private BigDecimal balance;

  @NotNull
  @NotBlank
  @Pattern(
      regexp =
          "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[1-5][a-fA-F0-9]{3}-[89abAB][a-fA-F0-9]{3}-[a-fA-F0-9]{12}$",
      message = "Must be a valid UUID")
  private String accountId;
}
