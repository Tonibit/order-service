package com.kafka.orderservice.domain.event.legacy;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentProcessedEvent implements DomainEvent {

  private String orderId;
  private String paymentId;
  private BigDecimal amount;
}
