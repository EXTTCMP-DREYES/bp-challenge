package com.darp.core.infrastructure.config;

import com.darp.core.infrastructure.input.mapper.AccountDtoMapper;
import io.r2dbc.spi.ConnectionFactory;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

@TestConfiguration
@Profile("test")
public class R2dbcConfig {
  @Bean
  public R2dbcEntityTemplate r2dbcEntityTemplate(ConnectionFactory connectionFactory) {
    return new R2dbcEntityTemplate(connectionFactory);
  }

  @Bean
  @SuppressWarnings("all")
  public AccountDtoMapper accountDtoMapper() {
    return Mappers.getMapper(AccountDtoMapper.class);
  }
}
