package com.darp.core.infrastructure.input.dto;

import java.math.BigDecimal;

public record AccountDto(
    String id,
    String number,
    String type,
    BigDecimal balance,
    String status,
    String customerId) {}
