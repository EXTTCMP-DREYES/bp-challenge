package com.darp.customers.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicatedCustomerException extends RuntimeException {
  public DuplicatedCustomerException(String message) {
    super(message);
  }
}
