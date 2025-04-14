package application.services;

import domain.events.AnimalMovedEvent;
import domain.events.DomainEventPublisher;
import domain.models.Animal;
import domain.models.Enclosure;
import domain.repository.AnimalRepository;
import domain.repository.EnclosureRepository;
import domain.vo.AnimalId;
import domain.vo.EnclosureId;

import java.util.Optional;

public class AnimalTransferService {

    private final AnimalRepository animalRepository;
    private final EnclosureRepository enclosureRepository;
    private final DomainEventPublisher eventPublisher;


    public AnimalTransferService(AnimalRepository animalRepository,
                                 EnclosureRepository enclosureRepository,
                                 DomainEventPublisher eventPublisher) {
        this.animalRepository = animalRepository;
        this.enclosureRepository = enclosureRepository;
        this.eventPublisher = eventPublisher;
    }

    public void transferAnimal(AnimalId animalId, EnclosureId newEnclosureId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new IllegalArgumentException("Animal not found: " + animalId));
        Enclosure newEnclosure = enclosureRepository.findById(newEnclosureId)
                .orElseThrow(() -> new IllegalArgumentException("Enclosure not found: " + newEnclosureId));

        Optional<Enclosure> oldEnclosureOpt = enclosureRepository.findById(animal.getEnclosureId());
        oldEnclosureOpt.ifPresent(oldEnc -> oldEnc.removeAnimal(animal.getId()));

        AnimalMovedEvent event = animal.move(newEnclosureId);

        newEnclosure.addAnimal(animalId);
        enclosureRepository.save(newEnclosure);
        oldEnclosureOpt.ifPresent(enclosureRepository::save);
        animalRepository.save(animal);

        eventPublisher.publish(event);
    }
}
