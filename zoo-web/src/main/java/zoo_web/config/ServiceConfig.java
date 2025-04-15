package zoo_web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zoo_web.application.repository.AnimalRepository;
import zoo_web.application.repository.EnclosureRepository;
import zoo_web.application.repository.FeedingScheduleRepository;
import zoo_web.application.services.AnimalTransferService;
import zoo_web.application.services.FeedingOrganizationService;
import zoo_web.application.services.ZooStatisticsService;
import zoo_web.domain.events.DomainEventPublisher;
import zoo_web.infrastructure.events.SimpleInMemoryEventPublisher;
import zoo_web.infrastructure.repositories.AnimalInMemoryRepository;
import zoo_web.infrastructure.repositories.EnclosureInMemoryRepository;
import zoo_web.infrastructure.repositories.FeedingScheduleInMemoryRepository;

@Configuration
public class ServiceConfig {

    @Bean
    public AnimalTransferService animalTransferService(
            AnimalRepository animalRepository,
            EnclosureRepository enclosureRepository,
            DomainEventPublisher eventPublisher
    ) {
        return new AnimalTransferService(animalRepository, enclosureRepository, eventPublisher);
    }

    @Bean
    public FeedingOrganizationService feedingService(
            FeedingScheduleRepository feedingScheduleRepository,
            DomainEventPublisher eventPublisher
    ) {
        return new FeedingOrganizationService(feedingScheduleRepository, eventPublisher);
    }

    @Bean
    public ZooStatisticsService zooStatisticsService(
            AnimalRepository animalRepository,
            EnclosureRepository enclosureRepository
    ) {
        return new ZooStatisticsService(animalRepository, enclosureRepository);
    }

    @Bean
    public DomainEventPublisher eventPublisher() {
        return new SimpleInMemoryEventPublisher();
    }

    @Bean
    public AnimalRepository animalRepository() {
        return new AnimalInMemoryRepository();
    }

    @Bean
    public EnclosureRepository enclosureRepository() {
        return new EnclosureInMemoryRepository();
    }

    @Bean
    public FeedingScheduleRepository feedingScheduleRepository() {
        return new FeedingScheduleInMemoryRepository();
    }

}
