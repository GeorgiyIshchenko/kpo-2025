package zoo_web.infrastructure.events;

import org.springframework.stereotype.Component;
import zoo_web.domain.events.DomainEventPublisher;

@Component
public class SimpleInMemoryEventPublisher implements DomainEventPublisher {

    @Override
    public void publish(Object domainEvent) {
        System.out.println("Domain Event: " + domainEvent);
    }

}
