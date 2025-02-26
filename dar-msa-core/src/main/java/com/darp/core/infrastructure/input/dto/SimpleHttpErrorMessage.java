package com.darp.core.infrastructure.input.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record SimpleHttpErrorMessage(
    String message, int status, LocalDateTime timestamp) {}
