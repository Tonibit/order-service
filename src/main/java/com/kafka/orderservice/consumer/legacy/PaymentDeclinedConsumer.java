package com.kafka.orderservice.consumer.legacy;

import com.kafka.orderservice.service.OrderService;
import org.springframework.stereotype.Component;

@Component
public class PaymentDeclinedConsumer {

  private final OrderService orderService;

  public PaymentDeclinedConsumer(OrderService orderService) {
    this.orderService = orderService;
  }

//  @KafkaListener(topics = "payments", groupId = "order-service")
//  public void consume(PaymentDeclinedEvent paymentDeclinedEvent) {
//
//    orderService.handlePaymentFailure(paymentDeclinedEvent);
//  }
}
