package com.darp.core.infrastructure.input.mapper;

import com.darp.core.domain.model.customer.Customer;
import com.darp.core.infrastructure.input.dto.CreateCustomerDto;
import com.darp.core.infrastructure.input.dto.CustomerDto;
import com.darp.core.infrastructure.input.dto.PartialUpdateCustomerDto;
import com.darp.core.infrastructure.input.dto.UpdateCustomerDto;
import org.mapstruct.*;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
    builder = @Builder(disableBuilder = true))
public interface CustomerDtoMapper {
  CustomerDto toDto(Customer customer);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  Customer toDomain(CreateCustomerDto dto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", constant = "ACTIVE")
  Customer toDomain(UpdateCustomerDto dto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", constant = "ACTIVE")
  Customer toDomain(PartialUpdateCustomerDto dto);

  default Integer toInteger(String integerValue) {
    return integerValue == null ? null : Integer.parseInt(integerValue);
  }
}
