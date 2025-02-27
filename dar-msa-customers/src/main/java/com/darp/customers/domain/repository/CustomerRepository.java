package com.darp.customers.domain.repository;

import com.darp.customers.domain.model.customer.Customer;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import reactor.core.publisher.Mono;

public interface CustomerRepository {
  Mono<Boolean> existsById(@NotNull @NotEmpty String id);

  Mono<Customer> findById(@NotNull @NotEmpty String id);

  Mono<Boolean> existsByIdentityNumber(@NotNull @NotEmpty String identityNumber);

  Mono<Customer> save(@NotNull Customer customer);

  Mono<Customer> update(@NotNull Customer customer);

  Mono<Void> deleteById(@NotNull @NotEmpty String id);
}
