package com.kafka.orderservice.controller;

import com.kafka.orderservice.domain.entity.OrderEntity;
import com.kafka.orderservice.service.OrderService;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  public ResponseEntity<String> createOrder(@RequestBody OrderEntity orderEntity) {
    return ResponseEntity.ok(orderService.createOrder(orderEntity));
  }

  @GetMapping("/id")
  public ResponseEntity<OrderEntity> getOrderById(@RequestBody UUID id) {
    return ResponseEntity.ok(orderService.getOrderById(id));
  }

  @GetMapping
  public ResponseEntity<List<OrderEntity>> getAllOrders() {
    return ResponseEntity.ok(orderService.findAllOrders());
  }

  @DeleteMapping
  public ResponseEntity deleteOrderById(UUID id) {
    orderService.deleteOrderById(id);
    return (ResponseEntity) ResponseEntity.ok();
  }
}
