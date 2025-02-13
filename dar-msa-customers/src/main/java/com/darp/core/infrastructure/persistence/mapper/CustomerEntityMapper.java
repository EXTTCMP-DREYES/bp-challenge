package com.darp.core.infrastructure.persistence.mapper;

import com.darp.core.domain.model.customer.Customer;
import com.darp.core.infrastructure.entity.CustomerEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    builder = @Builder(disableBuilder = true))
public interface CustomerEntityMapper {
  Customer toDomain(CustomerEntity entity);

  CustomerEntity toEntity(Customer domain);
}
