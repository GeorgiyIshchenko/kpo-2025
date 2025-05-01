package hse.kpo.kafka;

import hse.kpo.domains.Customer;
import hse.kpo.service.TrainingConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CustomerListener {

    @Autowired
    TrainingConsumer consumer;

    @KafkaListener(topics = "customers", groupId = "kpo")
    void listen(CustomerAddedEvent event) {
        consumer.handleCustomerEvent(event);
    }

}
