package com.darp.customers.infrastructure.input;

import com.darp.customers.domain.exception.NotFoundException;
import com.darp.customers.infrastructure.input.dto.SimpleHttpErrorMessage;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
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
}
