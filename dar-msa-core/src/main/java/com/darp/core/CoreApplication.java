package com.darp.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
@Slf4j
public class CoreApplication {

  public static void main(String[] args) {
    SpringApplication.run(CoreApplication.class, args);
  }
}
