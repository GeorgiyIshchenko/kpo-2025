package zoo_web.infrastructure.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import zoo_web.application.repository.AnimalRepository;
import zoo_web.domain.models.Animal;
import zoo_web.domain.vo.AnimalId;
import zoo_web.infrastructure.dto.AnimalDto;
import zoo_web.infrastructure.dto.request.CreateAnimalRequest;
import zoo_web.infrastructure.dto.response.AnimalMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import zoo_web.infrastructure.repositories.AnimalInMemoryRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/animals")
public class AnimalController {

    private final AnimalRepository animalRepository;
    private CreateAnimalRequest request;

    public AnimalController(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @GetMapping
    public List<AnimalDto> getAllAnimals() {
        return animalRepository.findAll().stream()
                .map(AnimalMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public AnimalDto getAnimal(@PathVariable UUID id) {
        Animal animal = animalRepository.findById(new AnimalId(id))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Animal not found"));
        return AnimalMapper.toDto(animal);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AnimalDto createAnimal(@Valid @RequestBody CreateAnimalRequest request) {
        this.request = request;
        Animal newAnimal = AnimalMapper.fromRequest(request);
        animalRepository.save(newAnimal);
        return AnimalMapper.toDto(newAnimal);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnimal(@PathVariable UUID id) {
        animalRepository.delete(new AnimalId(id));
    }

}