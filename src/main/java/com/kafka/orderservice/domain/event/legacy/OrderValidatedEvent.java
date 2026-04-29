package com.kafka.orderservice.domain.event.legacy;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderValidatedEvent {

  private String orderId;
  private BigDecimal totalPrice;

}
