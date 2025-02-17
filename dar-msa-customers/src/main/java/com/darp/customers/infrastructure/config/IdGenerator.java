package com.darp.customers.infrastructure.config;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class IdGenerator {
  public String generateId() {
    return UUID.randomUUID().toString();
  }
}
