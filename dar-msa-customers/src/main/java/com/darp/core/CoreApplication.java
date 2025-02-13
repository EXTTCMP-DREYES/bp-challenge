package com.darp.core;

import com.darp.core.domain.model.customer.Customer;
import com.darp.core.domain.model.customer.Status;
import com.darp.core.domain.model.person.Gender;
import com.darp.core.domain.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
@Slf4j
public class CoreApplication {

  public static void main(String[] args) {
    SpringApplication.run(CoreApplication.class, args);
  }

  @Bean
  public CommandLineRunner runner(CustomerRepository customerRepository) {
    return args -> {
      var customer =
          Customer.builder()
              .id("6542129e-23b8-47e6-a855-aa2155c3f33b")
              .fullName("Gabriela Borja")
              .age(25)
              .address("Tingo María")
              .gender(Gender.F)
              .phoneNumber("098123")
              .password("123")
              .status(Status.ACTIVE)
              .build();

      customerRepository
          //          .findById("5a425cd7-42ec-4715-9b8b-94dceafe6198")
          .save(customer)
          .subscribe(
              client -> log.info("Customer={}", client),
              error -> log.error("Error={}", error.getMessage()),
              () -> log.info("Customer Saved!"));
    };
  }
}
