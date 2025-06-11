package com.asyncshopping.paymentservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name="inbox")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class InboxEvent {
    @Id String eventId;
    Instant createdAt = Instant.now();

    public InboxEvent(String eventId) {
        this.eventId = eventId;
        this.createdAt = Instant.now();
    }

}
