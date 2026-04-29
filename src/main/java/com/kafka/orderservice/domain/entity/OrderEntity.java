package com.kafka.orderservice.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class OrderEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @CreatedDate
  @Column(updatable = false, nullable = false)
  private LocalDateTime createdDate;
  @LastModifiedDate
  @Column(nullable = false)
  private LocalDateTime lastModifiedDate;
  private String address;
  @Column(name = "postal_code")
  private String postalCode;
  @Column(name = "customer_name")
  private String customerName;
  @Enumerated(EnumType.STRING)
  private OrderStatus status;
  @Column(name = "comment")
  private String comment;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ItemEntity> items = new ArrayList<>();

  public void addItem(ItemEntity item) {
    items.add(item);
    item.setOrder(this);
  }
}
