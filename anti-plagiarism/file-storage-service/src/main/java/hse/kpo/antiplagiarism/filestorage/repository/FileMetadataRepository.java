package hse.kpo.antiplagiarism.filestorage.repository;

import hse.kpo.antiplagiarism.filestorage.model.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for file metadata operations.
 */
@Repository
public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
    
    /**
     * Find a file by its name.
     *
     * @param fileName The name of the file
     * @return Optional containing the file metadata if found
     */
    Optional<FileMetadata> findByFileName(String fileName);
    
    /**
     * Find a file by its MD5 hash.
     *
     * @param md5Hash The MD5 hash of the file
     * @return Optional containing the file metadata if found
     */
    Optional<FileMetadata> findByMd5Hash(String md5Hash);
    
    /**
     * Check if a file with the given MD5 hash exists.
     *
     * @param md5Hash The MD5 hash of the file
     * @return true if a file with the given hash exists, false otherwise
     */
    boolean existsByMd5Hash(String md5Hash);
}