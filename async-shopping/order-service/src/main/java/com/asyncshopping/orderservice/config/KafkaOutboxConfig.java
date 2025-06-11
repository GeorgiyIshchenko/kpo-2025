package com.asyncshopping.orderservice.config;

import com.asyncshopping.common.event.Events;
import com.asyncshopping.orderservice.model.OutboxEvent;
import com.asyncshopping.orderservice.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@Slf4j
@EnableKafka
public class KafkaOutboxConfig {

    private final OutboxRepository outbox;
    private final KafkaTemplate<String, Object> kafka;
    @Value("${spring.kafka.bootstrap-servers}") private String bootstrap;

    @Bean
    public ConsumerFactory<String, Events.PaymentStatusChangedEvent> cf() {
        Map<String,Object> cfg = new HashMap<>();
        cfg.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        cfg.put(ConsumerConfig.GROUP_ID_CONFIG, "payment");
        cfg.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        cfg.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, true);
        return new DefaultKafkaConsumerFactory<>(cfg,
                new StringDeserializer(),
                new JsonDeserializer<>(Events.PaymentStatusChangedEvent.class, false));
    }

    @Bean(name = "concurrentKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Events.PaymentStatusChangedEvent> kcl() {
        var f = new ConcurrentKafkaListenerContainerFactory<String, Events.PaymentStatusChangedEvent>();
        f.setConsumerFactory(cf());
        return f;
    }

    @Bean
    public NewTopic orderStatusTopic() {
        return TopicBuilder.name("order")
                .partitions(3).replicas(1).build();
    }

    @Scheduled(fixedDelay = 1000)
    public void publishOutbox() {
        List<OutboxEvent> batch = outbox.findTop100BySentFalse();
        batch.forEach(e -> kafka.send("order", e.getAggregateId().toString(), e.getPayload()));
        batch.forEach(e -> e.setSent(true));
        batch.forEach(e -> log.info("ORDER DEBUG:" + e.getId()));
        outbox.saveAll(batch);
    }
}
