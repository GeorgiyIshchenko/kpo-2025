package domain.models;

import domain.enums.AnimalSex;
import domain.enums.AnimalStatus;
import domain.enums.AnimalType;
import domain.events.AnimalMovedEvent;
import domain.vo.AnimalId;
import domain.vo.EnclosureId;
import lombok.Getter;

import java.util.Date;

@Getter
public class Animal {

    private AnimalId id;
    private AnimalType type;
    private String name;
    private Date bithDate;
    private AnimalSex sex;
    private String favoriteFood;
    private AnimalStatus status;
    private EnclosureId enclosureId;

    public Animal(AnimalId id,
                  AnimalType type,
                  String name,
                  Date bithDate,
                  AnimalSex sex,
                  String favoriteFood,
                  AnimalStatus status,
                  EnclosureId enclosureId) {
        if (id == null) {
            throw new IllegalArgumentException("Animal ID cannot be null");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        this.id = id;
        this.type = type;
        this.name = name;
        this.bithDate = bithDate;
        this.sex = sex;
        this.favoriteFood = favoriteFood;
        this.status = status;
        this.enclosureId = enclosureId;
    }

    public void feed() {

    }

    public void treat() {
        this.status = AnimalStatus.HEALTHY;
    }

    public AnimalMovedEvent move(EnclosureId newEnclosureId) {
        if (newEnclosureId == null) {
            throw new IllegalArgumentException("New enclosure cannot be null");
        }
        EnclosureId oldEnclosureId = this.enclosureId;
        this.enclosureId = newEnclosureId;
        return new AnimalMovedEvent(this.id, oldEnclosureId, newEnclosureId);
    }

}
