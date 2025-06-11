package com.asyncshopping.orderservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "outbox")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OutboxEvent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long aggregateId;
    private String aggregateType; // ORDER
    private String type; // ORDER_STATUS_CHANGED
    @Column(columnDefinition = "text")
    private String payload;
    private Boolean sent = false;
    private Instant createdAt = Instant.now();
}