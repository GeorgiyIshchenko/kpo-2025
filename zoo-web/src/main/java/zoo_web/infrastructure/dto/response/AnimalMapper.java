package zoo_web.infrastructure.dto.response;

import zoo_web.domain.enums.AnimalSex;
import zoo_web.domain.enums.AnimalStatus;
import zoo_web.domain.enums.AnimalType;
import zoo_web.domain.models.Animal;
import zoo_web.domain.vo.AnimalId;
import zoo_web.domain.vo.EnclosureId;
import zoo_web.infrastructure.dto.AnimalDto;
import zoo_web.infrastructure.dto.request.CreateAnimalRequest;

public class AnimalMapper {

    public static Animal fromRequest(CreateAnimalRequest request) {
        return new Animal(
                AnimalId.createNew(),
                AnimalType.valueOf(request.type),
                request.name,
                request.dateOfBirth,
                AnimalSex.valueOf(request.sex),
                request.favoriteFood,
                AnimalStatus.valueOf(request.status),
                request.enclosureId == null ? null : new EnclosureId(request.enclosureId)
        );
    }

    public static AnimalDto toDto(Animal animal) {
        AnimalDto dto = new AnimalDto();
        dto.id = animal.getId().getValue();
        dto.type = animal.getType().toString();
        dto.name = animal.getName();
        dto.dateOfBirth = animal.getBithDate();
        dto.favoriteFood = animal.getFavoriteFood();
        dto.sex = animal.getSex().name();
        dto.status = animal.getStatus().name();
        if (animal.getEnclosureId() != null) {
            dto.enclosureId = animal.getEnclosureId().getValue();
        }
        return dto;
    }


}
