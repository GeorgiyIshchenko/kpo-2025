package zoo_web.domain.repository;

import zoo_web.domain.models.FeedingSchedule;
import zoo_web.domain.vo.AnimalId;

import java.util.List;

public interface FeedingScheduleRepository {

    List<FeedingSchedule> findAll();
    List<FeedingSchedule> findByAnimalId(AnimalId animalId);
    FeedingSchedule save(FeedingSchedule enclosure);
    void deleteAll();

}
