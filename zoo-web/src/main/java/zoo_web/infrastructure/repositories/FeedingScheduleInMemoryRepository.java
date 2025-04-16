package zoo_web.infrastructure.repositories;

import zoo_web.domain.models.FeedingSchedule;
import zoo_web.domain.repository.FeedingScheduleRepository;
import zoo_web.domain.vo.AnimalId;

import java.util.*;

public class FeedingScheduleInMemoryRepository implements FeedingScheduleRepository {

    private final Map<UUID, List<FeedingSchedule>> store = new HashMap<>();


    @Override
    public List<FeedingSchedule> findByAnimalId(AnimalId animalId) {
        return store.get(animalId.getValue());
    }

    @Override
    public List<FeedingSchedule> findAll() {
        return store.values().stream().flatMap(List::stream).toList();
    }

    @Override
    public FeedingSchedule save(FeedingSchedule feedingSchedule) {
        List<FeedingSchedule> feedings = store.computeIfAbsent(feedingSchedule.getAnimalId().getValue(), k -> new ArrayList<>());
        feedings.add(feedingSchedule);
        return feedingSchedule;
    }

    @Override
    public void deleteAll() {
        store.clear();
    }

}
