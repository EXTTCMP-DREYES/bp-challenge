package com.darp.core.domain.events;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record ReportGenerationEvent(
    @NotBlank
        @Pattern(
            regexp =
                "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[1-5][a-fA-F0-9]{3}-[89abAB][a-fA-F0-9]{3}-[a-fA-F0-9]{12}$",
            message = "Must be a valid UUID")
        String correlationId,
    @NotBlank
        @Pattern(
            regexp =
                "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[1-5][a-fA-F0-9]{3}-[89abAB][a-fA-F0-9]{3}-[a-fA-F0-9]{12}$",
            message = "Must be a valid UUID")
        String customerId,
    LocalDate generatedAt) {}
