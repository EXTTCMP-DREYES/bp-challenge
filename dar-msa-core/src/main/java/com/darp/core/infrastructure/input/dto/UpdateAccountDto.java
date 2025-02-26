package com.darp.core.infrastructure.input.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder(toBuilder = true)
public record UpdateAccountDto(
    @Pattern(
            regexp = "^(SAVINGS|CURRENT)$",
            message = "Invalid type must be either SAVINGS or CURRENT")
        @NotNull
        @NotBlank
        String type) {}
