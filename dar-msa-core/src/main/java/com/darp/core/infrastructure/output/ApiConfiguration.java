package com.darp.core.infrastructure.output;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApiConfiguration {
  @Value("${api.customers.base-url}")
  private String customersBaseUrl;

  @Bean
  public WebClient customersWebClient() {
    return WebClient.create(customersBaseUrl);
  }
}
