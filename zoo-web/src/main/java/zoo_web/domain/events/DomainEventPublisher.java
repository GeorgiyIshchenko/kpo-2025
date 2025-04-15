package zoo_web.domain.events;

public interface DomainEventPublisher {
    void publish(Object event);
}
