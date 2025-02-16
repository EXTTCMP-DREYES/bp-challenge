package com.darp.core.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InsufficientFoundsException extends RuntimeException {
  public InsufficientFoundsException(String message) {
    super(message);
  }
}
