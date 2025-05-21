package hse.kpo.antiplagiarism.fileanalysis.repository;

import hse.kpo.antiplagiarism.fileanalysis.model.FileAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for file analysis operations.
 */
@Repository
public interface FileAnalysisRepository extends JpaRepository<FileAnalysis, Long> {
    
    /**
     * Find analysis by file ID.
     *
     * @param fileId The ID of the file
     * @return Optional containing the file analysis if found
     */
    Optional<FileAnalysis> findByFileId(Long fileId);
    
    /**
     * Check if analysis exists for a file.
     *
     * @param fileId The ID of the file
     * @return true if analysis exists, false otherwise
     */
    boolean existsByFileId(Long fileId);
}