package zoo_web.infrastructure.dto;

import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
public class AnimalDto {
    public UUID id;
    public String type;
    public String name;
    public Date dateOfBirth;
    public String favoriteFood;
    public String status;
    public UUID enclosureId;
}
