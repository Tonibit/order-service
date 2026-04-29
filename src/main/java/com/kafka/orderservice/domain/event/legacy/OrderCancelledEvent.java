package com.kafka.orderservice.domain.event.legacy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCancelledEvent implements DomainEvent {

  private String orderId;
  private String reason;
}
