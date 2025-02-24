package com.darp.customers.application.input.port;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface ReportEventMessageConsumer {
  void consume(@NotNull @NotBlank String rawMessage);
}
