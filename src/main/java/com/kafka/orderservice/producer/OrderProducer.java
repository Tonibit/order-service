package com.kafka.orderservice.producer;

import com.kafka.orderservice.domain.event.OrderEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {

  private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

  public OrderProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendOrderEvent(OrderEvent orderEvent) {
    kafkaTemplate.send("orders", orderEvent.getOrderId(), orderEvent);
  }

//  public void sendOrderRequested(OrderRequestedEvent event) {
//
//    EventEnvelope<OrderRequestedEvent> envelope =
//        new EventEnvelope<>("ORDER_REQUESTED", event);
//
//    kafkaTemplate.send("orders", event.getOrderId(), event);
//  }
//
//  public void sendOrderValidated(OrderValidatedEvent event) {
//
//    EventEnvelope<OrderValidatedEvent> envelope =
//        new EventEnvelope<>("ORDER_VALIDATED", event);
//
//    kafkaTemplate.send("orders", event.getOrderId(), event);
//  }
//
//  public void sendOrderCancelled(OrderCancelledEvent event) {
//
//    EventEnvelope<OrderCancelledEvent> envelope =
//        new EventEnvelope<>("ORDER_CANCELLED", event);
//
//    kafkaTemplate.send("orders", event.getOrderId(), event);
//  }
//
//  public void sendOrderConfirmed(OrderConfirmedEvent event) {
//
//    EventEnvelope<OrderConfirmedEvent> envelope =
//        new EventEnvelope<>("ORDER_CONFIRMED", event);
//
//    kafkaTemplate.send("orders", event.getOrderId(), event);
//  }

}
