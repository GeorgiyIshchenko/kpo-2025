package zoo_web.domain.models;

import zoo_web.domain.events.FeedingTimeEvent;
import zoo_web.domain.vo.AnimalId;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FeedingSchedule {

    private AnimalId animalId;
    private LocalDateTime feedingTime;
    private String food;
    private boolean isDone;

    public FeedingSchedule(AnimalId animalId,
                           LocalDateTime feedingTime,
                           String food) {
        if (animalId == null) {
            throw new IllegalArgumentException("Animal ID cannot be null");
        }
        if (feedingTime == null) {
            throw new IllegalArgumentException("Feeding time cannot be null");
        }
        this.animalId = animalId;
        this.feedingTime = feedingTime;
        this.food = food;
        this.isDone = false;
    }

    public void reschedule(LocalDateTime newFeedingTime) {
        this.feedingTime = newFeedingTime;
    }

    public FeedingTimeEvent markAsDone() {
        if (this.isDone) {
            throw new IllegalStateException("Feeding already done");
        }
        this.isDone = true;
        return new FeedingTimeEvent(this.animalId, this.feedingTime, this.food);
    }

}
