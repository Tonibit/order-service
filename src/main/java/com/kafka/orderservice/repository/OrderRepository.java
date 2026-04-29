package com.kafka.orderservice.repository;

import com.kafka.orderservice.domain.entity.OrderEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

  List<OrderEntity> findAll();

  Optional<OrderEntity> findById(UUID uuid);

  OrderEntity save(OrderEntity orderEntity);

  void deleteById(UUID id);

  void delete(OrderEntity entity);


}
