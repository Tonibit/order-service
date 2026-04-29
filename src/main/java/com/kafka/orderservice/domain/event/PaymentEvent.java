package com.kafka.orderservice.domain.event;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEvent {

  private String orderId;
  private String status;
  private String paymentId;
  private BigDecimal totalPrice;
  private String reason;
}
