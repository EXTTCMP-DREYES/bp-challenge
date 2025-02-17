package com.darp.core.application.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public record TransactionFilterParams(
    @NotBlank
        @Pattern(
            regexp =
                "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[1-5][a-fA-F0-9]{3}-[89abAB][a-fA-F0-9]{3}-[a-fA-F0-9]{12}$",
            message = "Must be a valid UUID")
        String customerId,
    @Size(min = 2, max = 2) List<LocalDate> dateRange) {}
