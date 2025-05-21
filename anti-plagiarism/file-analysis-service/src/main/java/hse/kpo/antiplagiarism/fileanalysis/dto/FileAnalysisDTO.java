package hse.kpo.antiplagiarism.fileanalysis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for transferring file analysis information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileAnalysisDTO {
    private Long id;
    private Long fileId;
    private Integer paragraphCount;
    private Integer wordCount;
    private Integer characterCount;
    private Boolean isPlagiarism;
    private Long similarFileId;
    private LocalDateTime analysisDate;
    private String wordCloudUrl;
}