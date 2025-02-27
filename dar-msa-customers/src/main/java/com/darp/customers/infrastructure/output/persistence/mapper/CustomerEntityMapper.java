package com.darp.customers.infrastructure.output.persistence.mapper;

import com.darp.customers.domain.model.customer.Customer;
import com.darp.customers.domain.model.customer.CustomerStatus;
import com.darp.customers.domain.model.person.PersonGender;
import com.darp.customers.infrastructure.output.entity.CustomerEntity;
import com.darp.customers.infrastructure.output.entity.PersonEntity;
import io.r2dbc.spi.Row;
import java.util.Map;
import org.mapstruct.*;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
    builder = @Builder(disableBuilder = true))
public interface CustomerEntityMapper {
  default Customer toDomain(Row row) {
    return Customer.builder()
        .id(row.get("id", String.class))
        .fullName(row.get("full_name", String.class))
        .identityNumber(row.get("identity_number", String.class))
        .gender(PersonGender.valueOf(row.get("gender", String.class)))
        .age(row.get("age", Integer.class))
        .address(row.get("address", String.class))
        .phoneNumber(row.get("phone_number", String.class))
        .password(row.get("password", String.class))
        .status(CustomerStatus.valueOf(row.get("status", String.class)))
        .build();
  }

  @Mapping(target = "id", source = "id")
  @Mapping(target = "password", source = "password")
  @Mapping(target = "status", source = "status")
  CustomerEntity toEntity(Customer domain);

  default Map<String, String> toMap(Customer domain) {
    return Map.ofEntries(
        Map.entry("id", domain.getId()),
        Map.entry("identity_number", domain.getIdentityNumber()),
        Map.entry("password", domain.getPassword()),
        Map.entry("status", domain.getStatus().name()));
  }

  PersonEntity toPersonEntity(Customer domain);

  default boolean existsFromCount(Row row) {
    var count = row.get(0, Long.class);
    return count != null && count > 0;
  }
}
