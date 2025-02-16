package com.darp.core.domain.repository;

import com.darp.core.domain.model.Account;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Mono;

@Valid
public interface AccountRepository {
  Mono<Account> findById(@NonNull @NotBlank String id);

  Mono<Account> save(@NonNull Account account);
}
