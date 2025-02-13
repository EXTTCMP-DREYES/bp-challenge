package com.darp.core.infrastructure.persistence.impl;

import com.darp.core.domain.model.customer.Customer;
import com.darp.core.domain.repository.CustomerRepository;
import com.darp.core.infrastructure.entity.CustomerEntity;
import com.darp.core.infrastructure.persistence.ReactiveCustomerRepository;
import com.darp.core.infrastructure.persistence.mapper.CustomerEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {
  private final ReactiveCustomerRepository reactiveCustomerRepository;
  private final R2dbcEntityTemplate template;
  private final CustomerEntityMapper customerEntityMapper;

  @Override
  public Mono<Customer> findById(String id) {
    return reactiveCustomerRepository.findById(id).map(customerEntityMapper::toDomain);
  }

  @Override
  public Mono<Customer> save(Customer customer) {
    var entity = customerEntityMapper.toEntity(customer);

    return template.insert(CustomerEntity.class).using(entity).map(customerEntityMapper::toDomain);
  }

  @Override
  public Mono<Void> deleteById(String id) {
    return reactiveCustomerRepository.deleteById(id);
  }
}
