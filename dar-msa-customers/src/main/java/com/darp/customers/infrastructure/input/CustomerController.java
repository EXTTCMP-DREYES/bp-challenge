package com.darp.customers.infrastructure.input;

import com.darp.customers.application.input.port.CustomerService;
import com.darp.customers.infrastructure.input.dto.CreateCustomerDto;
import com.darp.customers.infrastructure.input.dto.CustomerDto;
import com.darp.customers.infrastructure.input.dto.PartialUpdateCustomerDto;
import com.darp.customers.infrastructure.input.dto.UpdateCustomerDto;
import com.darp.customers.infrastructure.input.mapper.CustomerDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {
  private final CustomerService customerService;
  private final CustomerDtoMapper customerMapper;

  @GetMapping("/{id}")
  public Mono<CustomerDto> findById(@PathVariable String id) {
    log.info("|--> Find customer by id started: {}", id);
    return customerService.findById(id).map(customerMapper::toDto);
  }

  @PostMapping
  public Mono<CustomerDto> save(@Validated @RequestBody CreateCustomerDto customerDto) {
    log.info("|--> Save customer started: {}", customerDto);
    return customerService.save(customerMapper.toDomain(customerDto)).map(customerMapper::toDto);
  }

  @PutMapping("/{id}")
  public Mono<CustomerDto> update(
      @PathVariable String id, @Validated @RequestBody UpdateCustomerDto customerDto) {
    log.info("|--> Update customer by id started: {}", id);

    var customer = customerMapper.toDomain(customerDto);
    return customerService.update(id, customer).map(customerMapper::toDto);
  }

  @PatchMapping("/{id}")
  public Mono<CustomerDto> patch(
      @PathVariable String id, @Validated @RequestBody PartialUpdateCustomerDto customerDto) {
    log.info("|--> Patch customer by id started: {}", id);

    var customer = customerMapper.toDomain(customerDto);
    return customerService.update(id, customer).map(customerMapper::toDto);
  }

  @DeleteMapping("/{id}")
  public Mono<Void> deleteById(@PathVariable String id) {
    log.info("|--> Delete customer by id started: {}", id);
    return customerService.deleteById(id);
  }
}
