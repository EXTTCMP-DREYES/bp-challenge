package com.darp.core.infrastructure.output.api.maper;

import com.darp.core.domain.model.Customer;
import com.darp.core.infrastructure.output.api.dto.CustomerDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
    builder = @Builder(disableBuilder = true))
public interface CustomerDtoMapper {
  Customer toDomain(CustomerDto customerDto);
}
