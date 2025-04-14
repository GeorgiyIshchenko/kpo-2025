package domain.repository;

import domain.models.Animal;
import domain.vo.AnimalId;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository {

    Optional<Animal> findById(AnimalId animalId);
    List<Animal> findAll();
    Animal save(Animal animal);
    void delete(AnimalId animalId);

}
