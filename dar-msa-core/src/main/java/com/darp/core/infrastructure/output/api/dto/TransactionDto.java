package com.darp.core.infrastructure.output.api.dto;

import com.darp.core.domain.model.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionDto(
    @NotBlank
        @Pattern(
            regexp =
                "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[1-5][a-fA-F0-9]{3}-[89abAB][a-fA-F0-9]{3}-[a-fA-F0-9]{12}$",
            message = "Must be a valid UUID")
        String id,
    LocalDate executedAt,
    TransactionType type,
    @NotNull BigDecimal amount,
    @NotNull BigDecimal balance,
    @NotNull
        @NotBlank
        @Pattern(
            regexp =
                "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[1-5][a-fA-F0-9]{3}-[89abAB][a-fA-F0-9]{3}-[a-fA-F0-9]{12}$",
            message = "Must be a valid UUID")
        String accountId) {}
