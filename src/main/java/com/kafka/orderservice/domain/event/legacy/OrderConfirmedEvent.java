package com.kafka.orderservice.domain.event.legacy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderConfirmedEvent implements DomainEvent {

  private String orderId;
  private String address;
  private String CustomerName;

}
