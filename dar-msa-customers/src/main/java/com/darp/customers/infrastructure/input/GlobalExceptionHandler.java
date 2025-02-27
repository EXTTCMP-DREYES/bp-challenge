package com.darp.customers.infrastructure.input;

import com.darp.customers.domain.exception.JwtVerificationException;
import com.darp.customers.domain.exception.DuplicatedCustomerException;
import com.darp.customers.domain.exception.InvalidCredentialsException;
import com.darp.customers.domain.exception.NotFoundException;
import com.darp.customers.infrastructure.input.dto.SimpleHttpErrorMessage;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Mono<SimpleHttpErrorMessage> handleNotFoundException(NotFoundException e) {
    return Mono.just(
        SimpleHttpErrorMessage.builder()
            .message(e.getMessage())
            .status(HttpStatus.NOT_FOUND.value())
            .timestamp(LocalDateTime.now())
            .build());
  }

  @ExceptionHandler(DuplicatedCustomerException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public Mono<SimpleHttpErrorMessage> handleDuplicatedCustomerException(
      DuplicatedCustomerException e) {
    return Mono.just(
        SimpleHttpErrorMessage.builder()
            .message(e.getMessage())
            .status(HttpStatus.CONFLICT.value())
            .timestamp(LocalDateTime.now())
            .build());
  }

  @ExceptionHandler(WebExchangeBindException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Mono<SimpleHttpErrorMessage> handleWebExchangeBindException(WebExchangeBindException e) {
    var errorMessage =
        e.getAllErrors().stream()
            .map(ObjectError::getDefaultMessage)
            .collect(Collectors.joining(", "));

    return Mono.just(
        SimpleHttpErrorMessage.builder()
            .message(errorMessage)
            .status(HttpStatus.BAD_REQUEST.value())
            .timestamp(LocalDateTime.now())
            .build());
  }

  @ExceptionHandler({InvalidCredentialsException.class, JwtVerificationException.class})
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public Mono<SimpleHttpErrorMessage> handleInvalidCredentialsException(RuntimeException e) {
    return Mono.just(
        SimpleHttpErrorMessage.builder()
            .message(e.getMessage())
            .status(HttpStatus.UNAUTHORIZED.value())
            .timestamp(LocalDateTime.now())
            .build());
  }
}
