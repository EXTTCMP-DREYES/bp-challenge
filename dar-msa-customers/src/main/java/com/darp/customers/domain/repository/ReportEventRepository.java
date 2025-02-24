package com.darp.customers.domain.repository;

import com.darp.customers.domain.model.ReportGenerationEvent;
import jakarta.validation.constraints.NotNull;
import reactor.core.publisher.Mono;

public interface ReportEventRepository {
  Mono<ReportGenerationEvent> save(@NotNull ReportGenerationEvent event);
}
