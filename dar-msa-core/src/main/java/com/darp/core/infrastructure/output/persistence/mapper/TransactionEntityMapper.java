package com.darp.core.infrastructure.output.persistence.mapper;

import com.darp.core.domain.model.*;
import com.darp.core.infrastructure.output.persistence.entity.TransactionEntity;
import io.r2dbc.spi.Row;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.mapstruct.*;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
    builder = @Builder(disableBuilder = true))
public interface TransactionEntityMapper {
  TransactionEntity toEntity(Transaction domain);

  Transaction toDomain(TransactionEntity entity);

  @Mapping(target = "id", source = "transaction_id")
  @Mapping(target = "executedAt", source = "executed_at")
  @Mapping(target = "amount", source = "amount")
  @Mapping(target = "balance", source = "final_balance")
  @Mapping(target = "account.id", source = "account_id")
  @Mapping(target = "account.type", source = "account_type")
  @Mapping(target = "account.number", source = "account_number")
  @Mapping(target = "account.balance", source = "account_balance")
  @Mapping(target = "customer", ignore = true)
  default TransactionDetails toDomainDetails(Row row) {
    return TransactionDetails.builder()
        .id(row.get("transaction_id", String.class))
        .executedAt(row.get("executed_at", LocalDate.class))
        .amount(row.get("amount", BigDecimal.class))
        .type(TransactionType.valueOf(row.get("transaction_type", String.class)))
        .balance(row.get("final_balance", BigDecimal.class))
        .accountId(row.get("account_id", String.class))
        .account(
            Account.builder()
                .id(row.get("account_id", String.class))
                .type(AccountType.valueOf(row.get("account_type", String.class)))
                .number(row.get("account_number", String.class))
                .balance(row.get("account_balance", BigDecimal.class))
                .build())
        .build();
  }

  default String toString(Object value) {
    return value.toString();
  }

  default LocalDate toLocalDate(Object value) {
    return LocalDate.parse(value.toString());
  }
}
