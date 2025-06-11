package com.asyncshopping.orderservice.repository;

import com.asyncshopping.orderservice.model.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OutboxRepository extends JpaRepository<OutboxEvent, Long> {
    List<OutboxEvent> findTop100BySentFalse();
}