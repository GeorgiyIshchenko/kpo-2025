package zoo_web.infrastructure.dto;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class EnclosureDto {
    public UUID id;
    public String type;
    public int capacity;
    public List<UUID> animalIds;
}
