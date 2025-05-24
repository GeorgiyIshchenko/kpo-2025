package com.hse.analysis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hse.analysis.dto.AnalysisResponseDto;
import com.hse.analysis.service.AnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/analysis")
@RequiredArgsConstructor
public class AnalysisController {

    private final AnalysisService svc;

    @PostMapping("/{fileId}")
    public ResponseEntity<AnalysisResponseDto> analyze(@PathVariable UUID fileId,
                                                       @RequestParam(defaultValue = "30") int top) throws IOException {
        return ResponseEntity.ok(svc.start(fileId, top));
    }

    @GetMapping("/{id}")
    public AnalysisResponseDto get(@PathVariable UUID id) throws JsonProcessingException {
        return svc.get(id);
    }
}

