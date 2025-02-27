package com.darp.customers.application.input.port;

import com.darp.customers.domain.model.customer.Customer;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

public interface CustomerService {
  Mono<Customer> findById(@NotNull @NotEmpty String id);

  Mono<Customer> save(@Validated @NotNull Customer customer);

  Mono<Customer> update(@NotNull @NotEmpty String id, @Validated @NotNull Customer customer);

  Mono<Void> deleteById(@NotNull @NotEmpty String id);

  Mono<String> authenticateCustomer(@NotNull Customer customer);
}
