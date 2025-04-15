package zoo_web.infrastructure.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateFeedingRequest {

    @NotNull(message = "Animal ID is required")
    public UUID animalId;

    @NotNull(message = "Feeding time is required")
    @FutureOrPresent
    public LocalDateTime feedingTime;

    @NotBlank(message = "Food type is required")
    public String foodType;

}
