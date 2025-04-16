package zoo_web.presentation.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import zoo_web.domain.repository.EnclosureRepository;
import zoo_web.domain.models.Enclosure;
import zoo_web.domain.vo.EnclosureId;
import zoo_web.infrastructure.dto.EnclosureDto;
import zoo_web.infrastructure.dto.request.CreateEnclosureRequest;
import zoo_web.infrastructure.dto.response.EnclosureMapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/enclosures")
public class EnclosureController {

    private final EnclosureRepository enclosureRepository;
    private CreateEnclosureRequest request;

    public EnclosureController(EnclosureRepository enclosureRepository) {
        this.enclosureRepository = enclosureRepository;
    }

    @GetMapping
    public List<EnclosureDto> getAllEnclosures() {
        return enclosureRepository.findAll().stream().map(EnclosureMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public EnclosureDto getEnclosure(@PathVariable UUID id) {
        Enclosure enclosure = enclosureRepository.findById(new EnclosureId(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enclosure not found"));
        return EnclosureMapper.toDto(enclosure);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnclosureDto createEnclosure(@Valid @RequestBody CreateEnclosureRequest request) {
        this.request = request;
        Enclosure newEnclosure = EnclosureMapper.fromRequest(request);
        enclosureRepository.save(newEnclosure);
        return EnclosureMapper.toDto(newEnclosure);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEnclosure(@PathVariable UUID id) {
        enclosureRepository.delete(new EnclosureId(id));
    }

}
