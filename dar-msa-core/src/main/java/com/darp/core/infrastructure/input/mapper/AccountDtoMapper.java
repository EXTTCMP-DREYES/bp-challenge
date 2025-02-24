package com.darp.core.infrastructure.input.mapper;

import com.darp.core.domain.model.Account;
import com.darp.core.infrastructure.input.dto.AccountDto;
import com.darp.core.infrastructure.input.dto.CreateAccountDto;
import com.darp.core.infrastructure.input.dto.UpdateAccountDto;
import org.mapstruct.*;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
    builder = @Builder(disableBuilder = true))
public interface AccountDtoMapper {
  AccountDto toDto(Account domain);

  Account toDomain(AccountDto accountDto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "balance", source = "initialBalance")
  @Mapping(target = "status", ignore = true)
  Account toDomain(CreateAccountDto accountDto);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "balance", ignore = true)
  @Mapping(target = "number", ignore = true)
  @Mapping(target = "status", source = "dto.status")
  @Mapping(target = "type", source = "dto.type")
  @Mapping(target = "customerId", ignore = true)
  Account toDomain(String id, UpdateAccountDto dto);
}
