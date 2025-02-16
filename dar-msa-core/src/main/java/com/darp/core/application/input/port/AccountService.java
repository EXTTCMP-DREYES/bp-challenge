package com.darp.core.application.input.port;

import com.darp.core.domain.model.Account;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Mono;

@Valid
public interface AccountService {
  Mono<Account> findById(@NonNull @NotBlank String id);

  Mono<Account> save(@NonNull Account account);
}
