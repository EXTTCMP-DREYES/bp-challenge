package com.darp.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication(
    exclude = {
      ReactiveSecurityAutoConfiguration.class,
    })
@EnableR2dbcRepositories
@Slf4j
public class CustomersApplication {
  public static void main(String[] args) {
    SpringApplication.run(CustomersApplication.class, args);
  }
}
