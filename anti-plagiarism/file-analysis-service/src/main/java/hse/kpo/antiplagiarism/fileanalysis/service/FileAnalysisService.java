package hse.kpo.antiplagiarism.fileanalysis.service;

import hse.kpo.antiplagiarism.fileanalysis.client.FileStorageClient;
import hse.kpo.antiplagiarism.fileanalysis.dto.FileAnalysisDTO;
import hse.kpo.antiplagiarism.fileanalysis.model.FileAnalysis;
import hse.kpo.antiplagiarism.fileanalysis.repository.FileAnalysisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for file analysis operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FileAnalysisService {

    private final FileAnalysisRepository fileAnalysisRepository;
    private final FileStorageClient fileStorageClient;
    private final WebClient.Builder webClientBuilder;

    @Value("${word-cloud.api.url}")
    private String wordCloudApiUrl;

    @Value("${word-cloud.api.timeout}")
    private int wordCloudApiTimeout;

    /**
     * Analyze a file.
     *
     * @param fileId The ID of the file to analyze
     * @return The analysis results
     * @throws IOException If an I/O error occurs
     */
    public FileAnalysisDTO analyzeFile(Long fileId) throws IOException {
        // Check if file has already been analyzed
        Optional<FileAnalysis> existingAnalysis = fileAnalysisRepository.findByFileId(fileId);
        if (existingAnalysis.isPresent()) {
            return mapToDTO(existingAnalysis.get());
        }

        // Get file content from File Storage Service
        Resource fileResource = fileStorageClient.getFileContent(fileId);
        
        // Read file content
        String fileContent = readFileContent(fileResource);
        
        // Analyze file content
        int paragraphCount = countParagraphs(fileContent);
        int wordCount = countWords(fileContent);
        int characterCount = countCharacters(fileContent);
        
        // Check for plagiarism
        boolean isPlagiarism = false;
        Long similarFileId = null;
        
        List<FileAnalysis> existingAnalyses = fileAnalysisRepository.findAll();
        for (FileAnalysis analysis : existingAnalyses) {
            if (!analysis.getFileId().equals(fileId)) {
                Resource otherFileResource = fileStorageClient.getFileContent(analysis.getFileId());
                String otherFileContent = readFileContent(otherFileResource);
                
                if (isPlagiarism(fileContent, otherFileContent)) {
                    isPlagiarism = true;
                    similarFileId = analysis.getFileId();
                    break;
                }
            }
        }
        
        // Generate word cloud (optional)
        String wordCloudUrl = generateWordCloud(fileContent);
        
        // Save analysis results
        FileAnalysis fileAnalysis = FileAnalysis.builder()
                .fileId(fileId)
                .paragraphCount(paragraphCount)
                .wordCount(wordCount)
                .characterCount(characterCount)
                .isPlagiarism(isPlagiarism)
                .similarFileId(similarFileId)
                .analysisDate(LocalDateTime.now())
                .wordCloudUrl(wordCloudUrl)
                .build();
        
        FileAnalysis savedAnalysis = fileAnalysisRepository.save(fileAnalysis);
        return mapToDTO(savedAnalysis);
    }

    /**
     * Get analysis by file ID.
     *
     * @param fileId The ID of the file
     * @return The analysis results
     */
    public FileAnalysisDTO getAnalysisByFileId(Long fileId) {
        FileAnalysis analysis = fileAnalysisRepository.findByFileId(fileId)
                .orElseThrow(() -> new IllegalArgumentException("Analysis not found for file ID: " + fileId));
        return mapToDTO(analysis);
    }

    /**
     * Get all analyses.
     *
     * @return List of all analyses
     */
    public List<FileAnalysisDTO> getAllAnalyses() {
        return fileAnalysisRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Read file content from a Resource.
     *
     * @param resource The file resource
     * @return The file content as a string
     * @throws IOException If an I/O error occurs
     */
    private String readFileContent(Resource resource) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    /**
     * Count paragraphs in text.
     *
     * @param text The text to analyze
     * @return The number of paragraphs
     */
    private int countParagraphs(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        
        // Split by double newlines to count paragraphs
        String[] paragraphs = text.split("\\n\\s*\\n");
        return paragraphs.length;
    }

    /**
     * Count words in text.
     *
     * @param text The text to analyze
     * @return The number of words
     */
    private int countWords(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        
        // Split by whitespace to count words
        String[] words = text.trim().split("\\s+");
        return words.length;
    }

    /**
     * Count characters in text.
     *
     * @param text The text to analyze
     * @return The number of characters
     */
    private int countCharacters(String text) {
        if (text == null) {
            return 0;
        }
        
        return text.length();
    }

    /**
     * Check if two texts are plagiarism of each other.
     *
     * @param text1 The first text
     * @param text2 The second text
     * @return true if the texts are plagiarism, false otherwise
     */
    private boolean isPlagiarism(String text1, String text2) {
        if (text1 == null || text2 == null) {
            return false;
        }
        
        // For simplicity, we'll consider exact matches as plagiarism
        // In a real application, more sophisticated algorithms would be used
        return text1.equals(text2);
    }

    /**
     * Generate a word cloud for text.
     *
     * @param text The text to generate a word cloud for
     * @return The URL of the generated word cloud
     */
    private String generateWordCloud(String text) {
        try {
            // Prepare request to word cloud API
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("text", text);
            requestBody.put("width", 800);
            requestBody.put("height", 400);
            requestBody.put("backgroundColor", "white");
            
            // Call word cloud API
            String wordCloudUrl = webClientBuilder.build()
                    .post()
                    .uri(wordCloudApiUrl)
                    .body(Mono.just(requestBody), Map.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(java.time.Duration.ofMillis(wordCloudApiTimeout));
            
            return wordCloudUrl;
        } catch (Exception e) {
            log.error("Error generating word cloud: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Map a FileAnalysis entity to a FileAnalysisDTO.
     *
     * @param analysis The FileAnalysis entity
     * @return The FileAnalysisDTO
     */
    private FileAnalysisDTO mapToDTO(FileAnalysis analysis) {
        return FileAnalysisDTO.builder()
                .id(analysis.getId())
                .fileId(analysis.getFileId())
                .paragraphCount(analysis.getParagraphCount())
                .wordCount(analysis.getWordCount())
                .characterCount(analysis.getCharacterCount())
                .isPlagiarism(analysis.getIsPlagiarism())
                .similarFileId(analysis.getSimilarFileId())
                .analysisDate(analysis.getAnalysisDate())
                .wordCloudUrl(analysis.getWordCloudUrl())
                .build();
    }
}