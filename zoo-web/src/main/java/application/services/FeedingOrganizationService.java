package application.services;

import domain.events.DomainEventPublisher;
import domain.events.FeedingTimeEvent;
import domain.models.FeedingSchedule;
import domain.repository.FeedingScheduleRepository;
import domain.vo.AnimalId;

import java.time.LocalDateTime;
import java.util.List;

public class FeedingOrganizationService {

    private final FeedingScheduleRepository feedingScheduleRepository;
    private final DomainEventPublisher eventPublisher;

    public FeedingOrganizationService(FeedingScheduleRepository feedingScheduleRepository,
                                      DomainEventPublisher eventPublisher) {
        this.feedingScheduleRepository = feedingScheduleRepository;
        this.eventPublisher = eventPublisher;
    }

    public FeedingSchedule scheduleFeeding(AnimalId id, LocalDateTime time, String food) {
        FeedingSchedule feedingSchedule = new FeedingSchedule(id, time, food);
        return feedingScheduleRepository.save(feedingSchedule);
    }

    public void markFeedingAsDone(FeedingSchedule feedingSchedule) {
        if (feedingSchedule.isDone()) {
            throw new IllegalStateException("Feeding already done");
        }
        FeedingTimeEvent event = feedingSchedule.markAsDone();
        feedingScheduleRepository.save(feedingSchedule);
        eventPublisher.publish(event);
    }

    public List<FeedingSchedule> findFeedingsForAnimal(AnimalId id) {
        return feedingScheduleRepository.findByAnimalId(id);
    }

    public FeedingSchedule rescheduleFeeding(FeedingSchedule feedingSchedule, LocalDateTime newFeedingTime) {
        feedingSchedule.reschedule(newFeedingTime);
        return feedingScheduleRepository.save(feedingSchedule);
    }

}
