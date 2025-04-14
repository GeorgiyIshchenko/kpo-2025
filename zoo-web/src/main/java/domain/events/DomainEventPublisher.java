package domain.events;

public interface DomainEventPublisher {
    void publish(Object event);
}
