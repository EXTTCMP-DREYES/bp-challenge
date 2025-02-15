package com.darp.core.infrastructure.output.persistence.impl;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

import com.darp.core.domain.model.customer.Customer;
import com.darp.core.domain.repository.CustomerRepository;
import com.darp.core.infrastructure.output.entity.CustomerEntity;
import com.darp.core.infrastructure.output.entity.PersonEntity;
import com.darp.core.infrastructure.output.persistence.mapper.CustomerEntityMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Update;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {
  private final R2dbcEntityTemplate template;
  private final CustomerEntityMapper customerEntityMapper;

  @Override
  public Mono<Boolean> existsById(String id) {
    var sql = "select count(*) from customers c inner join persons p using(id) where id = :id";

    return template
        .getDatabaseClient()
        .sql(sql)
        .bind("id", id)
        .map(
            (row, metadata) -> {
              var count = row.get(0, Long.class);
              return count != null && count > 0;
            })
        .one();
  }

  @Override
  public Mono<Customer> findById(String id) {
    var sql = "select * from customers c inner join persons p using(id) where id = :id";

    return template
        .getDatabaseClient()
        .sql(sql)
        .bind("id", id)
        .map((row, metadata) -> customerEntityMapper.toDomain(row))
        .one();
  }

  @Override
  public Mono<Customer> save(Customer customer) {
    var personEntity = customerEntityMapper.toPersonEntity(customer);
    var customerArgs = customerEntityMapper.toMap(customer);

    return template
        .insert(PersonEntity.class)
        .into("persons")
        .using(personEntity)
        .flatMap(
            person -> {
              var sql =
                  "INSERT INTO public.customers (id, password, status) VALUES(:id, :password, :status)";

              return template
                  .getDatabaseClient()
                  .sql(sql)
                  .bindValues(customerArgs)
                  .fetch()
                  .rowsUpdated()
                  .thenReturn(person);
            })
        .thenReturn(customer);
  }

  @Override
  public Mono<Customer> update(Customer customer) {
    var personEntity = customerEntityMapper.toPersonEntity(customer);
    var entity = customerEntityMapper.toEntity(customer);

    Map<SqlIdentifier, Object> personAssignments = new HashMap<>();
    if (personEntity.getFullName() != null)
      personAssignments.put(SqlIdentifier.unquoted("full_name"), personEntity.getFullName());
    if (personEntity.getGender() != null)
      personAssignments.put(SqlIdentifier.unquoted("gender"), personEntity.getGender());
    if (personEntity.getAge() != null)
      personAssignments.put(SqlIdentifier.unquoted("age"), personEntity.getAge());
    if (personEntity.getAddress() != null)
      personAssignments.put(SqlIdentifier.unquoted("address"), personEntity.getAddress());
    if (personEntity.getPhoneNumber() != null)
      personAssignments.put(SqlIdentifier.unquoted("phone_number"), personEntity.getPhoneNumber());

    Map<SqlIdentifier, Object> customerAssignments = new HashMap<>();
    if (entity.getPassword() != null)
      customerAssignments.put(SqlIdentifier.unquoted("password"), entity.getPassword());
    if (entity.getStatus() != null)
      customerAssignments.put(SqlIdentifier.unquoted("status"), entity.getStatus());

    return template
        .update(PersonEntity.class)
        .matching(query(where("id").is(personEntity.getId())))
        .apply(Update.from(personAssignments))
        .then(
            template
                .update(CustomerEntity.class)
                .matching(query(where("id").is(entity.getId())))
                .apply(Update.from(customerAssignments))
                .then())
        .then(findById(entity.getId()));
  }

  @Override
  public Mono<Void> deleteById(String id) {
    return template
        .delete(PersonEntity.class)
        .from("persons")
        .matching(query(where("id").is(id)))
        .all()
        .then(template.delete(CustomerEntity.class).matching(query(where("id").is(id))).all())
        .then();
  }
}
