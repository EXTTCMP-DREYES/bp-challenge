package com.darp.customers.infrastructure.output.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

@Table(schema = "public", value = "reports_history")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ReportGenerationEventEntity {
  @NotBlank
  @Pattern(
      regexp =
          "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[1-5][a-fA-F0-9]{3}-[89abAB][a-fA-F0-9]{3}-[a-fA-F0-9]{12}$",
      message = "Must be a valid UUID")
  private UUID id;

  @NotBlank
  @Pattern(
      regexp =
          "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[1-5][a-fA-F0-9]{3}-[89abAB][a-fA-F0-9]{3}-[a-fA-F0-9]{12}$",
      message = "Must be a valid UUID")
  private UUID customerId;

  private LocalDateTime generatedAt;
}
