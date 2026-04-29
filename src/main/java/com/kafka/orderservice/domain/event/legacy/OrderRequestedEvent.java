package com.kafka.orderservice.domain.event.legacy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequestedEvent {

  private String orderId;
  private Integer totalItems;

}
