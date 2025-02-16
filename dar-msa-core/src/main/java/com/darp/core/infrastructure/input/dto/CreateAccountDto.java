package com.darp.core.infrastructure.input.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;

public record CreateAccountDto(
    @Pattern(regexp = "^[0-9]{13}$", message = "Invalid number") @NotNull @NotBlank String number,
    @Pattern(
            regexp = "^(SAVINGS|CURRENT)$",
            message = "Invalid type must be either SAVINGS or CURRENT")
        @NotNull
        @NotBlank
        String type,
    @Min(value = 0, message = "Initial balance must be greater than 0") @NotNull
        BigDecimal initialBalance,
    @NotNull
        @NotBlank
        @Pattern(
            regexp = "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[1-5][a-fA-F0-9]{3}-[89abAB][a-fA-F0-9]{3}-[a-fA-F0-9]{12}$",
            message = "Must be a valid UUID")
        String customerId) {}
