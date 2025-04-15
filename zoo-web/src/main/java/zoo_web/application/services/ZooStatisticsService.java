package zoo_web.application.services;

import zoo_web.domain.enums.AnimalStatus;
import zoo_web.domain.enums.AnimalType;
import zoo_web.application.repository.AnimalRepository;
import zoo_web.application.repository.EnclosureRepository;

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

}
