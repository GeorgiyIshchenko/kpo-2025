package hse.kpo.service;

import hse.kpo.domains.Customer;
import hse.kpo.kafka.CustomerAddedEvent;
import hse.kpo.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TrainingConsumer {

    private final CustomerRepository repository;

    public TrainingConsumer(CustomerRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void handleCustomerEvent(CustomerAddedEvent event) {
        Customer customer = new Customer(
                event.customerId(),
                event.customerName(),
                event.legPower(),
                event.handPower(),
                event.iq()
        );
        customer.setId(event.customerId());
        repository.save(customer);
        System.out.println("Customer has been added:" + customer.toString());
    }
}