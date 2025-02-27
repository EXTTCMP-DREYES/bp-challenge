package com.darp.customers.application.service.impl;

import com.darp.customers.application.input.port.CustomerService;
import com.darp.customers.domain.exception.DuplicatedCustomerException;
import com.darp.customers.domain.exception.InvalidCredentialsException;
import com.darp.customers.domain.exception.NotFoundException;
import com.darp.customers.domain.model.customer.Customer;
import com.darp.customers.domain.model.customer.CustomerStatus;
import com.darp.customers.domain.repository.CustomerRepository;
import com.darp.customers.infrastructure.config.IdGenerator;
import com.darp.customers.infrastructure.config.security.JwtTokenProvider;
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
  private final JwtTokenProvider tokenProvider;

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

    var newCustomer =
        customer.toBuilder() //
            .id(id)
            .password(encryptedPassword)
            .status(CustomerStatus.ACTIVE)
            .build();

    return customerRepository
        .existsByIdentityNumber(customer.getIdentityNumber())
        .flatMap(
            isIdentityNumberTaken -> {
              if (isIdentityNumberTaken) {
                return Mono.error(new DuplicatedCustomerException("Customer already exists"));
              }

              return customerRepository.save(newCustomer);
            })
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

  @Override
  public Mono<String> authenticateCustomer(Customer customer) {
    return customerRepository
        .findByIdentityNumber(customer.getIdentityNumber())
        .switchIfEmpty(Mono.error(new InvalidCredentialsException("Id number or password invalid")))
        .flatMap(
            storedCustomer -> {
              var passwordsMatch =
                  passwordEncoder.matches(customer.getPassword(), storedCustomer.getPassword());
              if (!passwordsMatch) {
                return Mono.error(new InvalidCredentialsException("Id number or password invalid"));
              }

              return Mono.fromCallable(() -> tokenProvider.generateToken(storedCustomer.getId()));
            });
  }
}
