package zoo_web.presentation.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import zoo_web.application.services.FeedingOrganizationService;
import zoo_web.domain.models.FeedingSchedule;
import zoo_web.domain.vo.AnimalId;
import zoo_web.infrastructure.dto.FeedingDto;
import zoo_web.infrastructure.dto.request.CreateFeedingRequest;
import zoo_web.infrastructure.dto.response.FeedingMapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/feedings")
public class FeedingController {

    private final FeedingOrganizationService feedingService;

    public FeedingController(FeedingOrganizationService feedingService) {
        this.feedingService = feedingService;
    }

    @GetMapping
    public List<FeedingDto> getAllFeedings() {
        return feedingService.findAllFeedings().stream().map(FeedingMapper::toDto).toList();
    }

    @GetMapping("/{animalId}")
    public List<FeedingDto> getFeedingsForAnimal(@PathVariable UUID animalId) {
        return feedingService.findFeedingsForAnimal(new AnimalId(animalId)).stream().map(FeedingMapper::toDto).toList();
    }

    @Operation(summary = "Create feeding schedule")
    @PostMapping
    public FeedingDto createFeeding(@Valid @RequestBody CreateFeedingRequest request) {
        return FeedingMapper.toDto(feedingService.scheduleFeeding(
                new AnimalId(request.animalId),
                request.feedingTime,
                request.foodType
        ));
    }

}
