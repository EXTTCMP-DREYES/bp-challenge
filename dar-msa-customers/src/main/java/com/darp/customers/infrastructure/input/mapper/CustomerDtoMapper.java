package com.darp.customers.infrastructure.input.mapper;

import com.darp.customers.domain.model.customer.Customer;
import com.darp.customers.infrastructure.input.dto.*;
import org.mapstruct.*;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
    builder = @Builder(disableBuilder = true))
public interface CustomerDtoMapper {
  CustomerDto toDto(Customer customer);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "address", ignore = true)
  @Mapping(target = "age", ignore = true)
  @Mapping(target = "fullName", ignore = true)
  @Mapping(target = "gender", ignore = true)
  @Mapping(target = "phoneNumber", ignore = true)
  Customer toDomain(CustomerCredentialsDto credentialsDto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  Customer toDomain(CreateCustomerDto dto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "identityNumber", ignore = true)
  @Mapping(target = "status", constant = "ACTIVE")
  Customer toDomain(UpdateCustomerDto dto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "identityNumber", ignore = true)
  @Mapping(target = "status", constant = "ACTIVE")
  Customer toDomain(PartialUpdateCustomerDto dto);

  default Integer toInteger(String integerValue) {
    return integerValue == null ? null : Integer.parseInt(integerValue);
  }
}
