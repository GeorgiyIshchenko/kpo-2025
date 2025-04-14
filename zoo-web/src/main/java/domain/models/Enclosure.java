package domain.models;

import domain.enums.AnimalType;
import domain.vo.AnimalId;
import domain.vo.EnclosureId;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Enclosure {

    private EnclosureId id;
    private AnimalType type;
    private int capacity;
    private Set<AnimalId> animalIds;

    public Enclosure(EnclosureId id, AnimalType type, int capacity) {
        if (id == null) {
            throw new IllegalArgumentException("Enclosure ID cannot be null");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.id = id;
        this.type = type;
        this.capacity = capacity;
        this.animalIds = new HashSet<>();
    }

    public void addAnimal(AnimalId animalId) {
        if (animalIds.size() >= capacity) {
            throw new IllegalStateException("Enclosure is full");
        }
        animalIds.add(animalId);
    }

    public void removeAnimal(AnimalId animalId) {
        animalIds.remove(animalId);
    }

    public void clean() {

    }

}
