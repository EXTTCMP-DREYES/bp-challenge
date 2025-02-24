package com.darp.customers.infrastructure.output.persistence;

import com.darp.customers.infrastructure.output.entity.ReportGenerationEventEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReportEventReactiveRepository
    extends ReactiveCrudRepository<ReportGenerationEventEntity, String> {}
