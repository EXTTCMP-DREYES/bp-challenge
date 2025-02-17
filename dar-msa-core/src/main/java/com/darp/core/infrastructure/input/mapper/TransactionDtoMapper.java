package com.darp.core.infrastructure.input.mapper;

import com.darp.core.domain.model.Transaction;
import com.darp.core.domain.model.TransactionDetails;
import com.darp.core.infrastructure.input.dto.TransactionDetailsDto;
import com.darp.core.infrastructure.output.api.dto.CreateTransactionDto;
import com.darp.core.infrastructure.output.api.dto.TransactionDto;
import java.math.BigDecimal;
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

  @Mapping(target = "executedAt", source = "executedAt")
  @Mapping(target = "customer", source = "customer.fullName")
  @Mapping(target = "accountNumber", source = "account.number")
  @Mapping(target = "accountType", source = "account.type")
  @Mapping(target = "type", source = "type")
  @Mapping(
      target = "initialBalance",
      expression = "java(calculateInitialBalance(transactionDetails))")
  @Mapping(target = "amount", source = "amount")
  @Mapping(target = "finalBalance", source = "balance")
  TransactionDetailsDto toDetailsDto(TransactionDetails transactionDetails);

  default BigDecimal calculateInitialBalance(TransactionDetails transaction) {
    return transaction.getBalance().subtract(transaction.getAmount());
  }
}
