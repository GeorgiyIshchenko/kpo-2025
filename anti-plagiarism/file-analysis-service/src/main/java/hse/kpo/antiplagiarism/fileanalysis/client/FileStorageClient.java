package hse.kpo.antiplagiarism.fileanalysis.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client for communicating with the File Storage Service.
 */
@FeignClient(name = "file-storage-service", fallback = FileStorageClientFallback.class)
public interface FileStorageClient {

    /**
     * Get a file as a Resource.
     *
     * @param fileId The ID of the file to get
     * @return The file as a Resource
     */
    @GetMapping("/files/{fileId}/content")
    Resource getFileContent(@PathVariable("fileId") Long fileId);
}