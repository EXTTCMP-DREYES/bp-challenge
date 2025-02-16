package com.darp.core.infrastructure.output.api.impl;

import com.darp.core.domain.exception.NotFoundException;
import com.darp.core.domain.model.Customer;
import com.darp.core.infrastructure.output.api.CustomersApi;
import com.darp.core.infrastructure.output.api.dto.CustomerDto;
import com.darp.core.infrastructure.output.api.maper.CustomerDtoMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomersApiImpl implements CustomersApi {
  private final WebClient customersWebClient;
  private final CustomerDtoMapper customerMapper;

  @Override
  public Mono<Customer> findById(@NonNull String id) {
    return customersWebClient
        .get()
        .uri("/{id}", id)
        .retrieve()
        .onStatus(
            status -> status == HttpStatus.NOT_FOUND,
            response -> {
              log.error("|--> Customer not found with id: {}", id);
              return Mono.error(new NotFoundException("Customer not found with id: " + id));
            })
        .bodyToMono(CustomerDto.class)
        .map(customerMapper::toDomain);
  }
}
