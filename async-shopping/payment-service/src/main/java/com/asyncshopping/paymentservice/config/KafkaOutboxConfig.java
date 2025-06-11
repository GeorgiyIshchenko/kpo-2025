package com.asyncshopping.paymentservice.config;

import com.asyncshopping.common.event.Events;
import com.asyncshopping.common.json.JacksonUtils;
import com.asyncshopping.paymentservice.model.OutboxEvent;
import com.asyncshopping.paymentservice.repository.OutboxRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    @Bean(name = "concurrentKafkaListenerContainerFactory")
    public ConsumerFactory<String, String> cf() {
        Map<String,Object> cfg = new HashMap<>();
        cfg.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        cfg.put(ConsumerConfig.GROUP_ID_CONFIG, "order");
        cfg.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        cfg.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, true);
        return new DefaultKafkaConsumerFactory<>(cfg,
                new StringDeserializer(),
                new StringDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kcl() {
        var f = new ConcurrentKafkaListenerContainerFactory<String,String>();
        f.setConsumerFactory(cf());
        return f;
    }

    @Bean
    public NewTopic paymentStatusTopic() {
        return TopicBuilder.name("payment").partitions(3).replicas(1).build();
    }

    @Scheduled(fixedDelay = 1000)
    public void publish() {
        List<OutboxEvent> batch = outbox.findTop100BySentFalse();
        batch.forEach(e -> {
            Events.PaymentStatusChangedEvent payment = null;
            try {
                payment = JacksonUtils.mapper().readValue(e.getPayload(), Events.PaymentStatusChangedEvent.class);
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
            kafka.send("payment", e.getAggregateId().toString(), payment);
        });
        batch.forEach(e -> e.setSent(true));
        outbox.saveAll(batch);
    }
}
