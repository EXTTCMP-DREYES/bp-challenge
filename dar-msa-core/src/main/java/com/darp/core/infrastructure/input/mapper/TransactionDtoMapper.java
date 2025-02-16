package com.darp.core.infrastructure.input.mapper;

import com.darp.core.domain.model.Transaction;
import com.darp.core.infrastructure.output.api.dto.CreateTransactionDto;
import com.darp.core.infrastructure.output.api.dto.TransactionDto;
import org.mapstruct.*;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
    builder = @Builder(disableBuilder = true))
public interface TransactionDtoMapper {
  TransactionDto toDto(Transaction domain);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "executedAt", ignore = true)
  @Mapping(target = "balance", ignore = true)
  @Mapping(target = "type", ignore = true)
  Transaction toDomain(CreateTransactionDto dto);
}
