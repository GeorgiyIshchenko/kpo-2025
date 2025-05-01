package hse.kpo.controllers;

import hse.kpo.domains.Customer;
import hse.kpo.kafka.TrainingCompletedEvent;
import hse.kpo.repositories.CustomerRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/training")
public class TrainingController {

    private final KafkaTemplate<String, TrainingCompletedEvent> kafkaTemplate;
    private final CustomerRepository customerRepository;

    public TrainingController(KafkaTemplate<String, TrainingCompletedEvent> kafkaTemplate,
                              CustomerRepository customerRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.customerRepository = customerRepository;
    }

    @PostMapping("/{customerId}/{type}")
    public CustomerResponse train(
            @PathVariable Integer customerId,
            @PathVariable String type
    ) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        switch (type.toLowerCase()) {
            case "leg":
                customer.setLegPower(customer.getLegPower() + 50);
                break;
            case "hand":
                customer.setHandPower(customer.getHandPower() + 50);
                break;
            case "iq":
                customer.setIq(customer.getIq() + 50);
                break;
            default:
                throw new IllegalArgumentException("Invalid training type");
        }

        customerRepository.save(customer);

        kafkaTemplate.send("training-updates",
                new TrainingCompletedEvent(customerId, type));

        return new CustomerResponse(
                "Customer %d %s training completed".formatted(customerId, type),
                "OK"
        );
    }
}
