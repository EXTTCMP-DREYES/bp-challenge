package com.darp.core.infrastructure.input;

import com.darp.core.domain.exception.DuplicateAccountException;
import com.darp.core.domain.exception.InsufficientFoundsException;
import com.darp.core.domain.exception.NotFoundException;
import com.darp.core.domain.exception.TransactionsReportException;
import com.darp.core.infrastructure.input.dto.SimpleHttpErrorMessage;
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

  @ExceptionHandler({DuplicateAccountException.class, TransactionsReportException.class})
  @ResponseStatus(HttpStatus.CONFLICT)
  public Mono<SimpleHttpErrorMessage> handleDuplicateAccountException(RuntimeException e) {
    return Mono.just(
        SimpleHttpErrorMessage.builder()
            .message(e.getMessage())
            .status(HttpStatus.CONFLICT.value())
            .timestamp(LocalDateTime.now())
            .build());
  }

  @ExceptionHandler(InsufficientFoundsException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public Mono<SimpleHttpErrorMessage> handleInsufficientFoundsException(
      InsufficientFoundsException e) {
    return Mono.just(
        SimpleHttpErrorMessage.builder()
            .message(e.getMessage())
            .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .timestamp(LocalDateTime.now())
            .build());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Mono<SimpleHttpErrorMessage> handleIllegalArgumentException(IllegalArgumentException e) {
    return Mono.just(
        SimpleHttpErrorMessage.builder()
            .message(e.getMessage())
            .status(HttpStatus.BAD_REQUEST.value())
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
}
