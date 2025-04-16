package zoo_web.application.services;

import zoo_web.domain.events.AnimalMovedEvent;
import zoo_web.domain.events.DomainEventPublisher;
import zoo_web.domain.models.Animal;
import zoo_web.domain.models.Enclosure;
import zoo_web.domain.repository.AnimalRepository;
import zoo_web.domain.repository.EnclosureRepository;
import zoo_web.domain.vo.AnimalId;
import zoo_web.domain.vo.EnclosureId;

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
