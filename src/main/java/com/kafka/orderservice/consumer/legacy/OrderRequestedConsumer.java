package com.kafka.orderservice.consumer.legacy;

import com.kafka.orderservice.service.OrderService;
import org.springframework.stereotype.Component;

@Component
public class OrderRequestedConsumer {

  private final OrderService orderService;

  public OrderRequestedConsumer(OrderService orderService) {
    this.orderService = orderService;
  }

//  @KafkaListener(topics = "orders", groupId = "order-service")
//  public void handle(OrderRequestedEvent event) {
//    orderService.handleOrderRequestedEvent(event);
//  }
}