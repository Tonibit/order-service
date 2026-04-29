package com.kafka.orderservice.consumer;

import com.kafka.orderservice.domain.event.PaymentEvent;
import com.kafka.orderservice.service.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentConsumer {

  private final OrderService orderService;

  public PaymentConsumer(OrderService orderService) {
    this.orderService = orderService;
  }

  @KafkaListener(topics = "payments", groupId = "order-service")
  public void consume(PaymentEvent event) {

    switch (event.getStatus()) {
      case "PROCESSED" -> orderService.handlePaymentSuccess(event);
      case "DECLINED" -> orderService.handlePaymentFailure(event);
    }
  }
  //  @KafkaListener(topics = "payments", groupId = "order-service")
//  public void consume(EventEnvelope envelope) {
//
//    switch (envelope.getEventType()) {
//      case "PAYMENT_PROCESSED" -> {
//        PaymentProcessedEvent processed =
//            (PaymentProcessedEvent) envelope.getPayload();
//        orderService.handlePaymentSuccess(processed);
//      }
//      case "PAYMENT_DECLINED" -> {
//        PaymentDeclinedEvent declined =
//            (PaymentDeclinedEvent) envelope.getPayload();
//        orderService.handlePaymentFailure(declined);
//      }
//    }
//  }
}
