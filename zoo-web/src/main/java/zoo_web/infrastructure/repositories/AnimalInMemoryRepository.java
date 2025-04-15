package zoo_web.infrastructure.repositories;

import zoo_web.domain.models.Animal;
import zoo_web.application.repository.AnimalRepository;
import zoo_web.domain.vo.AnimalId;

import java.util.*;

public class AnimalInMemoryRepository implements AnimalRepository {

    private final Map<UUID, Animal> store = new HashMap<>();

    @Override
    public Optional<Animal> findById(AnimalId id) {
        return Optional.ofNullable(store.get(id.getValue()));
    }

    @Override
    public List<Animal> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Animal save(Animal animal) {
        store.put(animal.getId().getValue(), animal);
        return animal;
    }

    @Override
    public void delete(AnimalId id) {
        store.remove(id.getValue());
    }

}
