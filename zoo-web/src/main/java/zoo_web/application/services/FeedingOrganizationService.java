package zoo_web.application.services;

import zoo_web.domain.events.DomainEventPublisher;
import zoo_web.domain.events.FeedingTimeEvent;
import zoo_web.domain.models.FeedingSchedule;
import zoo_web.domain.repository.FeedingScheduleRepository;
import zoo_web.domain.vo.AnimalId;

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

    public List<FeedingSchedule> findAllFeedings() {
        return feedingScheduleRepository.findAll();
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
