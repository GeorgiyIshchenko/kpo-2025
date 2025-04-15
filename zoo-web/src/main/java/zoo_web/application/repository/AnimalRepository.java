package zoo_web.application.repository;

import zoo_web.domain.models.Animal;
import zoo_web.domain.vo.AnimalId;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository {

    Optional<Animal> findById(AnimalId animalId);
    List<Animal> findAll();
    Animal save(Animal animal);
    void delete(AnimalId animalId);

}
