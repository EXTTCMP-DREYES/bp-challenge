package com.darp.customers.infrastructure.input.messaging;

import com.darp.customers.application.input.port.ReportEventMessageConsumer;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReportEventListener {
  private final ReactiveStringRedisTemplate redisTemplate;
  private final ReportEventMessageConsumer eventMessageConsumer;

  @PostConstruct
  public void listen() {
    redisTemplate
        .listenToChannel("report-generation")
        .map(ReactiveSubscription.Message::getMessage)
        .subscribe(
            eventMessageConsumer::consume,
            error ->
                log.error(
                    "|--> Error listening to report generation events: [{}]", error.getMessage()));
  }
}
