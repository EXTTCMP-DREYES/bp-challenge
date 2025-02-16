package com.darp.core.infrastructure.input.mapper;

import com.darp.core.domain.model.Account;
import com.darp.core.infrastructure.input.dto.AccountDto;
import com.darp.core.infrastructure.input.dto.CreateAccountDto;
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
}
