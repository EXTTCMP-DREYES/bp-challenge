package com.darp.core.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TransactionsReportException extends RuntimeException {
  public TransactionsReportException(String message) {
    super(message);
  }
}
