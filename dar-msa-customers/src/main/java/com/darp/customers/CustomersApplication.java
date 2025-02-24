package com.darp.customers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;

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

  @Bean
  CommandLineRunner runner(ReactiveStringRedisTemplate template) {
    return args ->
        template
            .listenToChannel("report-generation")
            .subscribe(
                message ->
                    log.info("|--> Report generation event received: {}", message.getMessage()),
                error -> log.error(error.getMessage()));
  }
}
