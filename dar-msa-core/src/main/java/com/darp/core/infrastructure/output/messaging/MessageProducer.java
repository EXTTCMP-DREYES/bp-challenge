package com.darp.core.infrastructure.output.messaging;

import reactor.core.publisher.Mono;

public interface MessageProducer<T> {
  Mono<Boolean> send(T message);
}
