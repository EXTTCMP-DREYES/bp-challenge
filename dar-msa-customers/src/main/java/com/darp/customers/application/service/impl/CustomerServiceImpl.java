package com.darp.customers.application.service.impl;

import com.darp.customers.application.input.port.CustomerService;
import com.darp.customers.domain.exception.NotFoundException;
import com.darp.customers.domain.model.customer.Customer;
import com.darp.customers.domain.model.customer.Status;
import com.darp.customers.domain.repository.CustomerRepository;
import com.darp.customers.infrastructure.config.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
  private final PasswordEncoder passwordEncoder;
  private final IdGenerator idGenerator;
  private final CustomerRepository customerRepository;

  @Override
  public Mono<Customer> findById(String id) {
    return customerRepository
        .findById(id)
        .switchIfEmpty(Mono.error(new NotFoundException("Customer not found")));
  }

  @Override
  public Mono<Customer> save(Customer customer) {
    var id = idGenerator.generateId();
    var encryptedPassword = passwordEncoder.encode(customer.getPassword());

    var storedCustomer =
        customer.toBuilder() //
            .id(id)
            .password(encryptedPassword)
            .status(Status.ACTIVE)
            .build();

    return customerRepository
        .save(storedCustomer)
        .doOnSuccess(savedCustomer -> log.info("|--> Customer saved: [{}].", savedCustomer.getId()))
        .doOnError(error -> log.error("|--> Error saving customer: {}.", error.getMessage()));
  }

  @Override
  public Mono<Customer> update(String id, Customer customer) {
    var rawPassword = customer.getPassword();
    var encryptedPassword = rawPassword != null ? passwordEncoder.encode(rawPassword) : null;
    var storedCustomer = customer.toBuilder().id(id).password(encryptedPassword).build();

    return customerRepository
        .existsById(id)
        .flatMap(
            customerExists -> {
              if (!customerExists) {
                return Mono.error(new NotFoundException("Customer not found"));
              }

              return customerRepository.update(storedCustomer);
            })
        .doOnSuccess(
            updatedCustomer -> log.info("|--> Customer updated: [{}].", updatedCustomer.getId()))
        .doOnError(error -> log.error("|--> Error updating customer: {}.", error.getMessage()));
  }

  @Override
  public Mono<Void> deleteById(String id) {
    return customerRepository
        .existsById(id)
        .flatMap(
            customerExists -> {
              if (!customerExists) {
                return Mono.error(new NotFoundException("Customer not found"));
              }

              return customerRepository.deleteById(id);
            })
        .doOnSuccess(unused -> log.info("|--> Customer deleted."))
        .doOnError(error -> log.error("|--> Error deleting customer: {}.", error.getMessage()));
  }
}
