package zoo_web.infrastructure.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class FeedingDto {
    public UUID animalId;
    public LocalDateTime feedingTime;
    public String foodType;
}
