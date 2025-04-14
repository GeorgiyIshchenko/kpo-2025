package domain.events;

import domain.vo.AnimalId;
import domain.vo.EnclosureId;
import lombok.Getter;

@Getter
public final class AnimalMovedEvent {

    private final AnimalId animalId;
    private final EnclosureId oldEnclosureId;
    private final EnclosureId newEnclosureId;

    public AnimalMovedEvent(AnimalId animalId,
                            EnclosureId oldEnclosureId,
                            EnclosureId newEnclosureId) {
        this.animalId = animalId;
        this.oldEnclosureId = oldEnclosureId;
        this.newEnclosureId = newEnclosureId;
    }

}
