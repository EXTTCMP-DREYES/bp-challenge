package com.darp.customers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
@Slf4j
public class CustomersApplication {
  public static void main(String[] args) {
    SpringApplication.run(CustomersApplication.class, args);
  }
}
