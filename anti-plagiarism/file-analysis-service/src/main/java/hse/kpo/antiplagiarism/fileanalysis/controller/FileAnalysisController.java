package hse.kpo.antiplagiarism.fileanalysis.controller;

import hse.kpo.antiplagiarism.fileanalysis.dto.ApiResponse;
import hse.kpo.antiplagiarism.fileanalysis.dto.FileAnalysisDTO;
import hse.kpo.antiplagiarism.fileanalysis.service.FileAnalysisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Controller for file analysis operations.
 */
@RestController
@RequestMapping("/analysis")
@RequiredArgsConstructor
@Slf4j
public class FileAnalysisController {

    private final FileAnalysisService fileAnalysisService;

    /**
     * Analyze a file.
     *
     * @param fileId The ID of the file to analyze
     * @return The analysis results
     */
    @PostMapping("/files/{fileId}")
    public ResponseEntity<?> analyzeFile(@PathVariable Long fileId) {
        try {
            FileAnalysisDTO analysis = fileAnalysisService.analyzeFile(fileId);
            return ResponseEntity.ok(ApiResponse.success(analysis, "File analyzed successfully"));
        } catch (IOException e) {
            log.error("Error analyzing file: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error("Error analyzing file: " + e.getMessage()));
        } catch (Exception e) {
            log.error("Unexpected error analyzing file: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.error("Unexpected error: " + e.getMessage()));
        }
    }

    /**
     * Get analysis by file ID.
     *
     * @param fileId The ID of the file
     * @return The analysis results
     */
    @GetMapping("/files/{fileId}")
    public ResponseEntity<?> getAnalysisByFileId(@PathVariable Long fileId) {
        try {
            FileAnalysisDTO analysis = fileAnalysisService.getAnalysisByFileId(fileId);
            return ResponseEntity.ok(ApiResponse.success(analysis, "Analysis retrieved successfully"));
        } catch (IllegalArgumentException e) {
            log.error("Analysis not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Unexpected error retrieving analysis: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.error("Unexpected error: " + e.getMessage()));
        }
    }

    /**
     * Get all analyses.
     *
     * @return List of all analyses
     */
    @GetMapping("/files")
    public ResponseEntity<?> getAllAnalyses() {
        try {
            List<FileAnalysisDTO> analyses = fileAnalysisService.getAllAnalyses();
            return ResponseEntity.ok(ApiResponse.success(analyses, "Analyses retrieved successfully"));
        } catch (Exception e) {
            log.error("Unexpected error retrieving analyses: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponse.error("Unexpected error: " + e.getMessage()));
        }
    }
}
