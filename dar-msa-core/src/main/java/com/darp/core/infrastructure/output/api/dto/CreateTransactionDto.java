package com.darp.core.infrastructure.output.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;

public record CreateTransactionDto(
    @NotNull BigDecimal amount,
    @NotNull
        @NotBlank
        @Pattern(
            regexp =
                "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[1-5][a-fA-F0-9]{3}-[89abAB][a-fA-F0-9]{3}-[a-fA-F0-9]{12}$",
            message = "Must be a valid UUID")
        String accountId) {
  public boolean isDeposit() {
    return amount.compareTo(BigDecimal.ZERO) > 0;
  }
}
