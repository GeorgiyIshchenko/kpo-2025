package com.asyncshopping.paymentservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name="outbox")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OutboxEvent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long aggregateId;
    String type; // PAYMENT_STATUS_CHANGED
    @Column(columnDefinition = "text")
    String payload;
    Boolean sent = false;
    Instant createdAt = Instant.now();
}
