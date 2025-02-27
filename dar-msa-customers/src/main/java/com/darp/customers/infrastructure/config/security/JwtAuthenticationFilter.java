package com.darp.customers.infrastructure.config.security;

import com.darp.customers.domain.exception.JwtVerificationException;
import com.darp.customers.domain.repository.CustomerRepository;
import com.darp.customers.shared.JwtConstants;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter implements WebFilter {
  private final JwtTokenProvider tokenProvider;
  private final CustomerRepository customerRepository;
  private final ServerSecurityContextRepository securityContextRepository;

  @Override
  @NonNull
  public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
    log.info("|--> JwtAuthenticationFilter started");

    var shouldProtect = checkEndpointProtection(exchange);
    if (!shouldProtect) {
      return chain.filter(exchange);
    }

    var jwt = getJwtFromRequest(exchange);
    if (!tokenProvider.checkTokenValidity(jwt)) {
      log.error("|--> Jwt: Invalid token");
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }

    var customerId = tokenProvider.getCustomerIdFromJWT(jwt);
    return customerRepository
        .existsById(customerId)
        .flatMap(
            exists -> {
              if (!exists) {
                log.info("|--> Jwt: Customer not found");

                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
              }

              var context = buildSecurityContext(customerId);

              return securityContextRepository
                  .save(exchange, context)
                  .then(chain.filter(exchange))
                  .doOnSuccess(v -> log.info("|--> Jwt: Customer authenticated!"));
            });
  }

  private boolean checkEndpointProtection(ServerWebExchange exchange) {
    var method = exchange.getRequest().getMethod();
    var path = exchange.getRequest().getPath().value();

    return method == HttpMethod.DELETE && path.startsWith("/api/customers");
  }

  @NonNull
  private String getJwtFromRequest(ServerWebExchange exchange) {
    var bearerHeader = exchange.getRequest().getHeaders().getFirst(JwtConstants.HEADER_NAME);
    var bearerToken =
        Optional.ofNullable(bearerHeader)
            .orElseThrow(
                () -> {
                  log.error("|--> Jwt: No token found");
                  return new JwtVerificationException("Token was not provided.");
                });

    var isPrefixUnavailable = !bearerToken.startsWith(JwtConstants.TOKEN_PREFIX);
    if (isPrefixUnavailable) {
      log.error("|--> Jwt: No token found");
      throw new JwtVerificationException("Token was not provided.");
    }

    return bearerToken.substring(7);
  }

  private SecurityContext buildSecurityContext(String customerId) {
    var authorities = List.of(new SimpleGrantedAuthority(JwtConstants.GENERIC_USER_ROLE));
    var authentication = new UsernamePasswordAuthenticationToken(customerId, null, authorities);

    return new SecurityContextImpl(authentication);
  }
}
