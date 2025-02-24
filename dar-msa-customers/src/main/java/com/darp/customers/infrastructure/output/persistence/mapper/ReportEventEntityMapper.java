package com.darp.customers.infrastructure.output.persistence.mapper;

import com.darp.customers.domain.model.ReportGenerationEvent;
import com.darp.customers.infrastructure.output.entity.ReportGenerationEventEntity;
import java.util.UUID;
import org.mapstruct.*;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
    builder = @Builder(disableBuilder = true))
public interface ReportEventEntityMapper {
  @Mapping(target = "id", source = "correlationId")
  @Mapping(target = "customerId", source = "customerId")
  @Mapping(target = "generatedAt", source = "generatedAt")
  ReportGenerationEventEntity toEntity(ReportGenerationEvent event);

  @Mapping(target = "correlationId", source = "id")
  @Mapping(target = "customerId", source = "customerId")
  @Mapping(target = "generatedAt", source = "generatedAt")
  ReportGenerationEvent toDomain(ReportGenerationEventEntity entity);

  default UUID toUuid(String id) {
    return UUID.fromString(id);
  }

  default String fromUuid(UUID id) {
    return id.toString();
  }
}
