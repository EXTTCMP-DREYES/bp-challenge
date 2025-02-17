package com.darp.core.domain.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import lombok.*;

@Valid
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
@Getter
@Setter
public class Account {
  @NotBlank
  @Pattern(
      regexp =
          "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[1-5][a-fA-F0-9]{3}-[89abAB][a-fA-F0-9]{3}-[a-fA-F0-9]{12}$",
      message = "Must be a valid UUID")
  private String id;

  @NotBlank
  @Pattern(regexp = "^\\d{13}$", message = "Must be a valid number")
  private String number;

  private AccountType type;

  private BigDecimal balance;

  private AccountStatus status;

  @NotBlank
  @Pattern(
      regexp =
          "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[1-5][a-fA-F0-9]{3}-[89abAB][a-fA-F0-9]{3}-[a-fA-F0-9]{12}$",
      message = "Must be a valid UUID")
  private String customerId;
}
