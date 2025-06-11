package com.asyncshopping.paymentservice.kafka;

import com.asyncshopping.common.event.Events;
import com.asyncshopping.common.json.JacksonUtils;
import com.asyncshopping.paymentservice.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderStatusListener {

    private final AccountService accounts;

    @KafkaListener(topics="order", groupId="order")
    public void onOrder(ConsumerRecord<String, String> record) throws JsonProcessingException {
        log.info("JSON: " + record.value());
        Events.OrderStatusChangedEvent evt = JacksonUtils.mapper().readValue(record.value(), Events.OrderStatusChangedEvent.class);
        String eventId = record.headers().lastHeader("eventId") == null ? record.key() : new String(record.headers().lastHeader("eventId").value());
        accounts.handleOrderEvent(evt, eventId);
    }
}
