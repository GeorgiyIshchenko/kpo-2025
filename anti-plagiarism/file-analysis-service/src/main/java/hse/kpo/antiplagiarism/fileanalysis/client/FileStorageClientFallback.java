package hse.kpo.antiplagiarism.fileanalysis.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * Fallback implementation for the FileStorageClient.
 * This is used when the File Storage Service is unavailable.
 */
@Component
@Slf4j
public class FileStorageClientFallback implements FileStorageClient {

    @Override
    public Resource getFileContent(Long fileId) {
        log.error("Fallback: Unable to get file content for file ID: {}", fileId);
        throw new RuntimeException("File Storage Service is currently unavailable");
    }
}