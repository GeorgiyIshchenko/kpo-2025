package com.asyncshopping.orderservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "orders")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private BigDecimal total;
    private String status; // NEW, PAID, FAILED
    private Instant createdAt;
    private Instant updatedAt;

    @PrePersist void prePersist() {
        createdAt = updatedAt = Instant.now();
    }
    @PreUpdate void preUpdate() {
        updatedAt = Instant.now();
    }
}