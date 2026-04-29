package com.kafka.orderservice.service;


import com.kafka.orderservice.domain.entity.ItemEntity;
import com.kafka.orderservice.domain.entity.OrderEntity;
import com.kafka.orderservice.domain.entity.OrderStatus;
import com.kafka.orderservice.domain.event.OrderEvent;
import com.kafka.orderservice.domain.event.PaymentEvent;
import com.kafka.orderservice.domain.event.ShipmentEvent;
import com.kafka.orderservice.producer.OrderProducer;
import com.kafka.orderservice.repository.OrderRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OrderService {

  private final OrderRepository orderRepository;
  private final OrderProducer orderProducer;

  public OrderService(OrderRepository orderRepository, OrderProducer orderProducer) {
    this.orderRepository = orderRepository;
    this.orderProducer = orderProducer;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public String createOrder(OrderEntity orderEntity) {
    orderEntity.setStatus(OrderStatus.REQUESTED);
    orderEntity.setCreatedDate(LocalDateTime.now());
    orderEntity.setLastModifiedDate(LocalDateTime.now());

    if (orderEntity.getItems() != null) {
      orderEntity.getItems().forEach(item -> item.setOrder(orderEntity));
    }
    orderRepository.save(orderEntity);

    OrderEvent orderEvent = new OrderEvent();
    orderEvent.setOrderId(orderEntity.getId().toString());
    orderEvent.setStatus(OrderStatus.REQUESTED.name());
    orderProducer.sendOrderEvent(orderEvent);

    System.out.println("ORDER IS REQUESTED AND PUBLISHED");

    return "Order REQUESTED, ID: " + orderEntity.getId();
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void handleOrderRequestedEvent(OrderEvent orderEvent) {
    Optional<OrderEntity> existingOrder = orderRepository.findById(
        UUID.fromString(orderEvent.getOrderId()));

    if (existingOrder.isEmpty() || existingOrder.get().getStatus() != OrderStatus.REQUESTED) {
      return;
    }

    OrderEntity order = existingOrder.get();

    if (!validate(order)) {
      order.setStatus(OrderStatus.NOT_VALID);
      orderRepository.save(order);
      return;
    }

    order.setStatus(OrderStatus.VALIDATED);
    orderRepository.save(order);
    System.out.println("ORDER IS SAVED AND VALID");

    orderEvent.setTotalPrice(order.getItems().stream()
        .map(ItemEntity::getPrice)
        .filter(Objects::nonNull)
        .reduce(BigDecimal.ZERO, BigDecimal::add));
    orderEvent.setStatus(OrderStatus.VALIDATED.name());
    orderProducer.sendOrderEvent(orderEvent);

    System.out.println("ORDER IS Published ");

  }

  public List<OrderEntity> findAllOrders() {
    return orderRepository.findAll();
  }

  public OrderEntity getOrderById(UUID id) {
    return orderRepository.findById(id).orElse(null);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void deleteOrderById(UUID id) {
    orderRepository.deleteById(id);
  }

  private boolean validate(OrderEntity orderEntity) {
    int totalQuantity = orderEntity.getItems().stream()
        .mapToInt(ItemEntity::getQuantity)
        .sum();
    return totalQuantity <= 8;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void handlePaymentSuccess(PaymentEvent paymentEvent) {
    OrderEntity orderEntity = getOrderById(UUID.fromString(paymentEvent.getOrderId()));
    orderEntity.setStatus(OrderStatus.CONFIRMED);
    orderEntity.setComment(paymentEvent.getPaymentId() + " was processed");
    orderRepository.save(orderEntity);

    OrderEvent orderEvent = getOrderEvent(orderEntity);
    orderProducer.sendOrderEvent(orderEvent);
    System.out.println("Order with id " + orderEntity.getId() + " was confirmed ");
  }

  private OrderEvent getOrderEvent(OrderEntity orderEntity) {
    OrderEvent orderEvent = new OrderEvent();
    orderEvent.setOrderId(orderEntity.getId().toString());
    orderEvent.setStatus(orderEntity.getStatus().name());
    orderEvent.setAddress(orderEntity.getAddress());
    orderEvent.setCustomerName(orderEntity.getCustomerName());
    orderEvent.setPostalCode(orderEntity.getPostalCode());
    return orderEvent;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void handlePaymentFailure(PaymentEvent paymentEvent) {
    OrderEntity orderEntity = getOrderById(UUID.fromString(paymentEvent.getOrderId()));
    orderEntity.setStatus(OrderStatus.CANCELLED);
    orderEntity.setComment(paymentEvent.getReason());
    orderRepository.save(orderEntity);

//    OrderCancelledEvent orderCancelledEvent = new OrderCancelledEvent();
//    orderCancelledEvent.setOrderId(orderEntity.getId().toString());
//    orderCancelledEvent.setReason(event.getReason());
    OrderEvent orderEvent = new OrderEvent();
    orderEvent.setOrderId(orderEntity.getId().toString());
    orderEvent.setStatus(orderEntity.getStatus().name());
    orderProducer.sendOrderEvent(orderEvent);

    System.out.println(
        "Order with id " + orderEntity.getId() + " was declined. Reason: "
            + paymentEvent.getReason());
  }

  @Transactional
  public void handleShipmentEvent(ShipmentEvent shipmentEvent) {
    OrderEntity orderEntity = getOrderById(UUID.fromString(shipmentEvent.getOrderId()));
    orderEntity.setStatus(OrderStatus.COMPLETED);

    orderRepository.save(orderEntity);

    System.out.println("Order was COMPLETED " + orderEntity.getId());

  }
}
