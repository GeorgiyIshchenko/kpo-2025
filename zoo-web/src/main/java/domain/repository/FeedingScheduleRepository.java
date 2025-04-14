package domain.repository;

import domain.models.Enclosure;
import domain.models.FeedingSchedule;
import domain.vo.AnimalId;
import domain.vo.EnclosureId;

import java.util.List;
import java.util.Optional;

public interface FeedingScheduleRepository {

    List<FeedingSchedule> findAll();
    List<FeedingSchedule> findByAnimalId(AnimalId animalId);
    FeedingSchedule save(FeedingSchedule enclosure);

}
