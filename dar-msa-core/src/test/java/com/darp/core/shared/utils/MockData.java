package com.darp.core.shared.utils;

import com.darp.core.domain.model.Account;
import com.darp.core.domain.model.AccountType;
import com.darp.core.domain.model.Customer;
import com.darp.core.infrastructure.input.dto.CreateAccountDto;
import com.darp.core.infrastructure.input.dto.UpdateAccountDto;
import java.math.BigDecimal;

public class MockData {
  private MockData() {}

  public static final String ACCOUNT_NUMBER = "1234561234560";

  public static final CreateAccountDto CREATE_ACCOUNT_DTO =
      CreateAccountDto.builder()
          .number("1234561234561")
          .type("SAVINGS")
          .initialBalance(BigDecimal.TEN)
          .customerId("c3996159-662e-486d-b88f-a40ac3fddcc4")
          .build();

  public static final UpdateAccountDto UPDATE_ACCOUNT_DTO =
      UpdateAccountDto.builder().type("CURRENT").build();

  public static final Account ACCOUNT =
      Account.builder()
          .id("b00ff9a0-8749-4004-bb6f-c074756a6691")
          .number(ACCOUNT_NUMBER)
          .type(AccountType.SAVINGS)
          .balance(BigDecimal.TEN)
          .customerId("c3996159-662e-486d-b88f-a40ac3fddcc4")
          .build();

  public static final Customer CUSTOMER =
      Customer.builder()
          .id("1")
          .fullName("John Doe")
          .gender("M")
          .age(30)
          .address("123 Main St")
          .phoneNumber("+1234567890")
          .status("ACTIVE")
          .build();
}
