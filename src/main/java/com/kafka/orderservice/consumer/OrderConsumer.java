package com.kafka.orderservice.consumer;

import com.kafka.orderservice.domain.entity.OrderStatus;
import com.kafka.orderservice.domain.event.OrderEvent;
import com.kafka.orderservice.service.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

  private final OrderService orderService;

  public OrderConsumer(OrderService orderService) {
    this.orderService = orderService;
  }

  @KafkaListener(topics = "orders", groupId = "order-service")
  public void processOrder(OrderEvent event) {
    if (OrderStatus.REQUESTED.toString().equals(event.getStatus())) {
      orderService.handleOrderRequestedEvent(event);
    }
  }

}
