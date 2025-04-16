package zoo_web.infrastructure.dto.response;

import zoo_web.domain.models.FeedingSchedule;
import zoo_web.domain.vo.AnimalId;
import zoo_web.infrastructure.dto.FeedingDto;
import zoo_web.infrastructure.dto.request.CreateFeedingRequest;

public class FeedingMapper {

    public static FeedingSchedule fromRequest(CreateFeedingRequest request){
        return new FeedingSchedule(
                new AnimalId(request.animalId),
                request.feedingTime,
                request.foodType
        );
    }

    public static FeedingDto toDto(FeedingSchedule feedingSchedule){
        FeedingDto dto = new FeedingDto();
        dto.animalId = feedingSchedule.getAnimalId().getValue();
        dto.feedingTime = feedingSchedule.getFeedingTime();
        dto.foodType = feedingSchedule.getFood();
        return dto;
    }

}
