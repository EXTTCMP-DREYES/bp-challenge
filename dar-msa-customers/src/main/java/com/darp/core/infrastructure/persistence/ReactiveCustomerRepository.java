package com.darp.core.infrastructure.persistence;

import com.darp.core.infrastructure.entity.CustomerEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ReactiveCustomerRepository extends ReactiveCrudRepository<CustomerEntity, String> {
  @NonNull
  @Query("select * from customers c inner join persons p using(id) where id = :customerId")
  Mono<CustomerEntity> findById(@NonNull String customerId);
}
