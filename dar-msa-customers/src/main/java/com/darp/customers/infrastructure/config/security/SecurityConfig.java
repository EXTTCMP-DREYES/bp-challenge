package com.darp.customers.infrastructure.config.security;

import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public ServerSecurityContextRepository securityContextRepository() {
    return new WebSessionServerSecurityContextRepository();
  }

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(
      ServerHttpSecurity http, JwtAuthenticationFilter authenticationFilter) {
    return http.securityContextRepository(securityContextRepository())
        .csrf(ServerHttpSecurity.CsrfSpec::disable)
        .cors(ServerHttpSecurity.CorsSpec::disable)
        .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
        .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
        .authorizeExchange(
            exchanges ->
                exchanges
                    .pathMatchers(HttpMethod.POST, "/customers").authenticated()
                    .anyExchange().permitAll())
        .addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
        .build();
  }

  @Bean
  public SecretKey jwtKey() {
    return Jwts.SIG.HS256.key().build();
  }
}
