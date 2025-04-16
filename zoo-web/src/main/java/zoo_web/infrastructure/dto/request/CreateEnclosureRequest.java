package zoo_web.infrastructure.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class CreateEnclosureRequest {

    @NotBlank(message = "Type is required")
    @Schema(example = "PREDATOR")
    public String type;

    @NotBlank(message = "Capacity is required")
    @Schema(example = "5")
    public int capacity;

}
