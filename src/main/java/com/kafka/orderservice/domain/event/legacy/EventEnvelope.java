package com.kafka.orderservice.domain.event.legacy;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "eventType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = OrderRequestedEvent.class, name = "ORDER_REQUESTED"),
    @JsonSubTypes.Type(value = OrderValidatedEvent.class, name = "ORDER_VALIDATED")
})
public class EventEnvelope<T extends DomainEvent> {

  private String eventId;
  private String eventType;
  private Instant timestamp;
  private T payload;

  public EventEnvelope(String eventType, T payload) {
    this.eventId = UUID.randomUUID().toString();
    this.eventType = eventType;
    this.timestamp = Instant.now();
    this.payload = payload;
  }

}
