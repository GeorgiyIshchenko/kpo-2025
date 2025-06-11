package com.asyncshopping.orderservice.service;

import com.asyncshopping.common.event.Events;
import com.asyncshopping.common.json.JacksonUtils;
import com.asyncshopping.orderservice.model.Order;
import com.asyncshopping.orderservice.model.OutboxEvent;
import com.asyncshopping.orderservice.repository.OrderRepository;
import com.asyncshopping.orderservice.repository.OutboxRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orders;
    private final OutboxRepository outbox;

    @Transactional
    public Order create(Long userId, BigDecimal total) {
        Order o = orders.save(Order.builder()
                .userId(userId)
                .total(total)
                .status("NEW")
                .build());
        enqueueEvent(o, null, "NEW");
        return o;
    }

    @Transactional
    public Order updateStatus(Long orderId, String newStatus) {
        Order o = orders.findById(orderId).orElseThrow();
        String old = o.getStatus();
        o.setStatus(newStatus);
        enqueueEvent(o, old, newStatus);
        return o;
    }

    private void enqueueEvent(Order o, String oldSt, String newSt) {
        Events.OrderStatusChangedEvent evt = new Events.OrderStatusChangedEvent(
                o.getId(), o.getUserId(), oldSt, newSt,
                o.getTotal(), Instant.now());
        String json = null;
        try {
            json = JacksonUtils.mapper().writeValueAsString(evt);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        outbox.save(OutboxEvent.builder()
                .aggregateId(o.getId())
                .aggregateType("ORDER")
                .type("ORDER_STATUS_CHANGED")
                .payload(json)
                .sent(false)
                .build());
    }

    public Order findById(Long orderId) {
        return orders.findById(orderId).orElseThrow();
    }

    public List<Order> findAll() {
        return orders.findAll();
    }

}