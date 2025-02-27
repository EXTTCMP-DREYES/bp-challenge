package com.darp.core.domain.repository;

import com.darp.core.domain.model.Account;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Mono;

@Valid
public interface AccountRepository {
  Mono<Account> findById(@NonNull @NotBlank String id);

  Mono<Boolean> existsByNumber(@NonNull @NotBlank String number);

  Mono<Account> save(@NonNull Account account);

  Mono<Account> update(@NonNull Account account);

  Mono<Void> delete(@NonNull Account id);
}
