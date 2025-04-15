package zoo_web.infrastructure.dto.response;

import zoo_web.domain.enums.AnimalType;
import zoo_web.domain.models.Enclosure;
import zoo_web.domain.vo.EnclosureId;
import zoo_web.infrastructure.dto.EnclosureDto;
import zoo_web.infrastructure.dto.request.CreateEnclosureRequest;

public class EnclosureMapper {

    public static Enclosure fromRequest(CreateEnclosureRequest request) {
        return new Enclosure(
                EnclosureId.createNew(),
                AnimalType.valueOf(request.type),
                request.capacity
        );
    }

    public static EnclosureDto toDto(zoo_web.domain.models.Enclosure enclosure) {
        EnclosureDto dto = new EnclosureDto();
        dto.id = enclosure.getId().getValue();
        dto.type = enclosure.getType().toString();
        dto.capacity = enclosure.getCapacity();
        return dto;
    }

}
