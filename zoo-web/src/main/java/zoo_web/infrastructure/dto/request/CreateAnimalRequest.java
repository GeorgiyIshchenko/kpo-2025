package zoo_web.infrastructure.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.util.Date;
import java.util.UUID;

public class CreateAnimalRequest {

    @NotBlank(message = "Type is required")
    @Schema(example = "PREDATOR")
    public String type;

    @NotBlank(message = "Name is required")
    @Schema(example = "Lion")
    public String name;

    @NotNull(message = "Date of birth cannot be null")
    @PastOrPresent(message = "Date of birth must be in the past or today")
    public Date dateOfBirth;

    @NotBlank(message = "Favorite food is required")
    @Schema(example = "Meat")
    public String favoriteFood;

    @NotBlank(message = "Sex is required")
    @Schema(example = "MALE")
    public String sex;

    @NotBlank(message = "Status is required")
    @Schema(example = "HEALTHY")
    public String status;

    public UUID enclosureId;

}
