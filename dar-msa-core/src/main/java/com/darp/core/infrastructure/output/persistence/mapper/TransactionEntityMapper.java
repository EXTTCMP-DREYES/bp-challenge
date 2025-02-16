package com.darp.core.infrastructure.output.persistence.mapper;

import com.darp.core.domain.model.Transaction;
import com.darp.core.infrastructure.output.persistence.entity.TransactionEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
    builder = @Builder(disableBuilder = true))
public interface TransactionEntityMapper {
  TransactionEntity toEntity(Transaction domain);

  Transaction toDomain(TransactionEntity entity);
}
