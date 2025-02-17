package com.darp.core.infrastructure.input.dto;

import java.math.BigDecimal;

public record TransactionDetailsDto(
    String executedAt,
    String customer,
    String type,
    String accountNumber,
    String accountType,
    BigDecimal initialBalance,
    BigDecimal amount,
    BigDecimal finalBalance) {}
