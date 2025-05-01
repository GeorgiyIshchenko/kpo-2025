package hse.kpo.service;

import hse.kpo.domains.Customer;
import hse.kpo.kafka.CustomerAddedEvent;
import hse.kpo.repositories.CustomerRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TrainingConsumer {

    private final CustomerRepository repository;

    public TrainingConsumer(CustomerRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "customers", groupId = "kpo")
    public void handleCustomerEvent(CustomerAddedEvent event) {
        Customer customer = new Customer(
                event.customerName(),
                event.legPower(),
                event.handPower(),
                event.iq()
        );
        customer.setId(event.customerId());
        System.out.println("Customer has been added:" + customer.toString());
        repository.save(customer);
    }
}