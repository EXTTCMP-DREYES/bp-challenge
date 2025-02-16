package com.darp.core.infrastructure.output.api;

import com.darp.core.domain.model.Customer;
import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Mono;

public interface CustomersApi {
  Mono<Customer> findById(@NonNull @NotBlank String id);
}
