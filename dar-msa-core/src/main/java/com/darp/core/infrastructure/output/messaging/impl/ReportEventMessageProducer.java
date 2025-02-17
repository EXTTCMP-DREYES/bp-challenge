package com.darp.core.infrastructure.output.messaging.impl;

import com.darp.core.domain.events.ReportGenerationEvent;
import com.darp.core.infrastructure.output.messaging.MessageProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReportEventMessageProducer implements MessageProducer<ReportGenerationEvent> {
  private static final String TOPIC = "report-generation";

  private final ReactiveRedisTemplate<String, String> redisTemplate;
  private final ObjectMapper objectMapper;

  @Override
  public Mono<Boolean> send(ReportGenerationEvent message) {
    return redisTemplate
        .convertAndSend(TOPIC, serialize(message))
        .map(totalOfReaders -> totalOfReaders > 0)
        .doOnSuccess(
            result -> log.info("|--> Report generation event sent successfully: [{}]", message))
        .doOnError(
            error ->
                log.error("|--> Error sending report generation event: [{}]", error.getMessage()));
  }

  private String serialize(ReportGenerationEvent message) {
    try {
      return objectMapper.writeValueAsString(message);
    } catch (JsonProcessingException e) {
      log.error("|--> Error serializing report generation event: [{}]", e.getMessage());
      return "";
    }
  }
}
