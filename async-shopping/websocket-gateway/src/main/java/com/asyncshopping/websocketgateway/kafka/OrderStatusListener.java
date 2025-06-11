package com.asyncshopping.websocketgateway.kafka;

import com.asyncshopping.common.event.Events;
import com.asyncshopping.common.json.JacksonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusListener {

    private static final Logger log = LoggerFactory.getLogger(OrderStatusListener.class);
    private final SimpMessagingTemplate messagingTemplate;

    public OrderStatusListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = "order", groupId = "websocket")
    public void onEvent(ConsumerRecord<String, String> record) throws JsonProcessingException {
        log.debug("Received {}", record.value());
        Events.OrderStatusChangedEvent evt = JacksonUtils.mapper().readValue(record.value(), Events.OrderStatusChangedEvent.class);
        messagingTemplate.convertAndSend("/topic/orders/" + evt.orderId(), evt);
    }
}