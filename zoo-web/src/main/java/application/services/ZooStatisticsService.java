package application.services;

import domain.enums.AnimalStatus;
import domain.enums.AnimalType;
import domain.repository.AnimalRepository;
import domain.repository.EnclosureRepository;

public class ZooStatisticsService {

    private final AnimalRepository animalRepository;
    private final EnclosureRepository enclosureRepository;

    public ZooStatisticsService(AnimalRepository animalRepository,
                                EnclosureRepository enclosureRepository) {
        this.animalRepository = animalRepository;
        this.enclosureRepository = enclosureRepository;
    }

    public int countAnimals() {
        return animalRepository.findAll().size();
    }

    public int countEnclosures() {
        return enclosureRepository.findAll().size();
    }

    public int countFreeEnclosures() {
        return (int)enclosureRepository.findAll().stream()
                .filter(enclosure -> enclosure.getAnimalIds().isEmpty())
                .count();
    }

    public int getNumberOfSickAnimals() {
        return (int)animalRepository.findAll().stream()
                .filter(animal -> animal.getStatus() == AnimalStatus.SICK)
                .count();
    }

    public int getNumberOfHealthyAnimals() {
        return (int)animalRepository.findAll().stream()
                .filter(animal -> animal.getStatus() == AnimalStatus.HEALTHY)
                .count();
    }

    public int getNumberOfPredators() {
        return (int)animalRepository.findAll().stream()
                .filter(animal -> animal.getType() == AnimalType.PREDATOR)
                .count();
    }

    public int getNumberOfHerbivores() {
        return (int)animalRepository.findAll().stream()
                .filter(animal -> animal.getType() == AnimalType.HERB)
                .count();
    }

    public int getNumberOfBirds() {
        return (int)animalRepository.findAll().stream()
                .filter(animal -> animal.getType() == AnimalType.BIRD)
                .count();
    }

    public int getNumberOfAquariums() {
        return (int)animalRepository.findAll().stream()
                .filter(animal -> animal.getType() == AnimalType.AQUARIUM)
                .count();
    }

}
