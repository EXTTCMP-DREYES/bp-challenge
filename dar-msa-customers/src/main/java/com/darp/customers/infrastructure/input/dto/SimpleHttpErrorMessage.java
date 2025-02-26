package com.darp.customers.infrastructure.input.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SimpleHttpErrorMessage(
    String message, int status, LocalDateTime timestamp) {}
