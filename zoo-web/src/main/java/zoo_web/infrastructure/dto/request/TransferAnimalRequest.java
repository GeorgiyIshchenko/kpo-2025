package zoo_web.infrastructure.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class TransferAnimalRequest {
    @NotBlank(message = "New enclosure id is required")
    public UUID newEnclosureId;
}
