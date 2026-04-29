package com.kafka.orderservice.domain.entity;

public enum OrderStatus {
  REQUESTED,
  VALIDATED,
  NOT_VALID,
  CONFIRMED,
  COMPLETED,
  CANCELLED
}
