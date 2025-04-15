package zoo_web.infrastructure.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import zoo_web.application.services.AnimalTransferService;
import zoo_web.domain.vo.AnimalId;
import zoo_web.domain.vo.EnclosureId;
import zoo_web.infrastructure.dto.request.TransferAnimalRequest;

import java.util.UUID;

@RestController
@RequestMapping("/transfer")
public class AnimalTransferController {

    private final AnimalTransferService animalTransferService;

    public AnimalTransferController(AnimalTransferService animalTransferService) {
        this.animalTransferService = animalTransferService;
    }

    @Operation(summary = "Transfer animal to another enclosure")
    @PostMapping("/animals/{animalId}")
    @ResponseStatus(HttpStatus.OK)
    public void transferAnimal(@PathVariable UUID animalId, @Valid @RequestBody TransferAnimalRequest request) {
        animalTransferService.transferAnimal(new AnimalId(animalId), new EnclosureId(request.newEnclosureId));
    }

}
