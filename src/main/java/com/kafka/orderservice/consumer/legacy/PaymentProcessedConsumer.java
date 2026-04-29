package com.kafka.orderservice.consumer.legacy;

import com.kafka.orderservice.service.OrderService;
import org.springframework.stereotype.Component;

@Component
public class PaymentProcessedConsumer {

  private final OrderService orderService;

  public PaymentProcessedConsumer(OrderService orderService) {
    this.orderService = orderService;
  }

//  @KafkaListener(topics = "payments", groupId = "order-service")
//  public void consume(PaymentProcessedEvent paymentProcessedEvent) {
//
//    orderService.handlePaymentSuccess(paymentProcessedEvent);
//  }

}
