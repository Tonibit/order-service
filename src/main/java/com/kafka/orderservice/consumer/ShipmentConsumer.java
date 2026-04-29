package com.kafka.orderservice.consumer;

import com.kafka.orderservice.domain.event.ShipmentEvent;
import com.kafka.orderservice.service.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ShipmentConsumer {

  private final OrderService orderService;

  public ShipmentConsumer(OrderService orderService) {
    this.orderService = orderService;
  }

  @KafkaListener(topics = "shipments", groupId = "order-service")
  public void consume(ShipmentEvent shipmentEvent) {
    if ("DELIVERED".equals(shipmentEvent.getStatus())) {
      orderService.handleShipmentEvent(shipmentEvent);
    }
  }
}
