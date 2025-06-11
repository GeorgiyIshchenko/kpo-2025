package com.asyncshopping.orderservice.kafka;

import com.asyncshopping.common.event.Events;
import com.asyncshopping.orderservice.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentStatusListener {

    private final OrderService orders;

    @KafkaListener(topics = "payment", groupId = "payment")
    public void onPayment(ConsumerRecord<String, Events.PaymentStatusChangedEvent> record) throws JsonProcessingException {
        log.info("JSON: " + record.value().toString());
        orders.updateStatus(record.value().orderId(),
                "SUCCESS".equals(record.value().result()) ? "PAID" : "FAILED");
    }
}
