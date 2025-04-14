package domain.events;

import domain.vo.AnimalId;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public final class FeedingTimeEvent {

    private final AnimalId animalId;
    private final LocalDateTime feedingTime;
    private final String food;

    public FeedingTimeEvent(AnimalId animalId, LocalDateTime feedingTime, String food) {
        this.animalId = animalId;
        this.feedingTime = feedingTime;
        this.food = food;
    }

}
