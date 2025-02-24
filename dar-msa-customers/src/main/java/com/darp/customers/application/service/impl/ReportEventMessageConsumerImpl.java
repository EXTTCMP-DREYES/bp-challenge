package com.darp.customers.application.service.impl;

import com.darp.customers.application.input.port.ReportEventMessageConsumer;
import com.darp.customers.domain.model.ReportGenerationEvent;
import com.darp.customers.domain.repository.ReportEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReportEventMessageConsumerImpl implements ReportEventMessageConsumer {
  private final ReportEventRepository reportEventRepository;
  private final ObjectMapper objectMapper;

  @Override
  public void consume(String rawMessage) {
    var event = deserialize(rawMessage);

    reportEventRepository
        .save(event)
        .subscribe(
            savedEvent ->
                log.info("|--> Report generation event saved: [{}].", savedEvent.correlationId()),
            error ->
                log.error("|--> Error saving report generation event: {}.", error.getMessage()));
  }

  private ReportGenerationEvent deserialize(String rawMessage) {
    try {
      return objectMapper.readValue(rawMessage, ReportGenerationEvent.class);
    } catch (JsonProcessingException e) {
      log.error("|--> Error deserializing report generation event: [{}]", e.getMessage());
      throw new RuntimeException(e);
    }
  }
}
