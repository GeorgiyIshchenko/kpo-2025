package hse.kpo.antiplagiarism.fileanalysis.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity to store file analysis results.
 */
@Entity
@Table(name = "file_analysis")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_id", nullable = false)
    private Long fileId;

    @Column(name = "paragraph_count")
    private Integer paragraphCount;

    @Column(name = "word_count")
    private Integer wordCount;

    @Column(name = "character_count")
    private Integer characterCount;

    @Column(name = "is_plagiarism")
    private Boolean isPlagiarism;

    @Column(name = "similar_file_id")
    private Long similarFileId;

    @Column(name = "analysis_date", nullable = false)
    private LocalDateTime analysisDate;

    @Column(name = "word_cloud_url")
    private String wordCloudUrl;
}