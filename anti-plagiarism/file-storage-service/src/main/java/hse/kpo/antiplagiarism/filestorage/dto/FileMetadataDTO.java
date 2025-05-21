package hse.kpo.antiplagiarism.filestorage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for transferring file metadata information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileMetadataDTO {
    private Long id;
    private String fileName;
    private String originalFileName;
    private String contentType;
    private Long fileSize;
    private LocalDateTime uploadDate;
    private String md5Hash;
}