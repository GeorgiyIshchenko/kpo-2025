package com.hse.analysis.config;

import com.hse.analysis.dto.FileUploadedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}") private String bootstrap;

    @Bean
    public ConsumerFactory<String, FileUploadedEvent> cf() {
        Map<String,Object> cfg = new HashMap<>();
        cfg.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        cfg.put(ConsumerConfig.GROUP_ID_CONFIG, "analysis");
        cfg.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(cfg,
                new StringDeserializer(),
                new JsonDeserializer<>(FileUploadedEvent.class, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, FileUploadedEvent> kcl() {
        var f = new ConcurrentKafkaListenerContainerFactory<String,FileUploadedEvent>();
        f.setConsumerFactory(cf());
        return f;
    }
}

