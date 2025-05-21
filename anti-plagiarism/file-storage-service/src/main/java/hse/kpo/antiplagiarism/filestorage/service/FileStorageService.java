package hse.kpo.antiplagiarism.filestorage.service;

import hse.kpo.antiplagiarism.filestorage.dto.FileMetadataDTO;
import hse.kpo.antiplagiarism.filestorage.model.FileMetadata;
import hse.kpo.antiplagiarism.filestorage.repository.FileMetadataRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for file storage operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageService {

    private final FileMetadataRepository fileMetadataRepository;

    @Value("${file.storage.location}")
    private String fileStorageLocation;

    private Path fileStoragePath;

    @PostConstruct
    public void init() {
        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        try {
            Files.createDirectories(fileStoragePath);
        } catch (IOException e) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored", e);
        }
    }

    /**
     * Store a file and its metadata.
     *
     * @param file The file to store
     * @return The stored file's metadata
     * @throws IOException If an I/O error occurs
     */
    public FileMetadataDTO storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Cannot store empty file");
        }

        if (!isTextFile(file)) {
            throw new IllegalArgumentException("Only .txt files are allowed");
        }

        String originalFileName = file.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originalFileName);
        String fileName = UUID.randomUUID() + "." + extension;

        Path targetLocation = fileStoragePath.resolve(fileName);

        // Calculate MD5 hash
        String md5Hash;
        try (InputStream inputStream = file.getInputStream()) {
            md5Hash = DigestUtils.md5DigestAsHex(inputStream);
        }

        // Check if file with same hash already exists
        Optional<FileMetadata> existingFile = fileMetadataRepository.findByMd5Hash(md5Hash);
        if (existingFile.isPresent()) {
            return mapToDTO(existingFile.get());
        }

        // Store the file
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
        }

        // Save metadata
        FileMetadata metadata = FileMetadata.builder()
                .fileName(fileName)
                .originalFileName(originalFileName)
                .filePath(targetLocation.toString())
                .contentType(file.getContentType())
                .fileSize(file.getSize())
                .uploadDate(LocalDateTime.now())
                .md5Hash(md5Hash)
                .build();

        FileMetadata savedMetadata = fileMetadataRepository.save(metadata);
        return mapToDTO(savedMetadata);
    }

    /**
     * Get a file as a Resource.
     *
     * @param fileId The ID of the file to get
     * @return The file as a Resource
     * @throws IOException If an I/O error occurs
     */
    public Resource getFileAsResource(Long fileId) throws IOException {
        FileMetadata metadata = fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new IllegalArgumentException("File not found with id: " + fileId));

        Path filePath = Paths.get(metadata.getFilePath());
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new IOException("Could not read file: " + metadata.getFileName());
        }
    }

    /**
     * Get file metadata by ID.
     *
     * @param fileId The ID of the file
     * @return The file metadata
     */
    public FileMetadataDTO getFileMetadata(Long fileId) {
        FileMetadata metadata = fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new IllegalArgumentException("File not found with id: " + fileId));
        return mapToDTO(metadata);
    }

    /**
     * Get all file metadata.
     *
     * @return List of all file metadata
     */
    public List<FileMetadataDTO> getAllFileMetadata() {
        return fileMetadataRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Check if a file with the same content already exists.
     *
     * @param md5Hash The MD5 hash of the file
     * @return true if a file with the same content exists, false otherwise
     */
    public boolean fileExists(String md5Hash) {
        return fileMetadataRepository.existsByMd5Hash(md5Hash);
    }

    /**
     * Map a FileMetadata entity to a FileMetadataDTO.
     *
     * @param metadata The FileMetadata entity
     * @return The FileMetadataDTO
     */
    private FileMetadataDTO mapToDTO(FileMetadata metadata) {
        return FileMetadataDTO.builder()
                .id(metadata.getId())
                .fileName(metadata.getFileName())
                .originalFileName(metadata.getOriginalFileName())
                .contentType(metadata.getContentType())
                .fileSize(metadata.getFileSize())
                .uploadDate(metadata.getUploadDate())
                .md5Hash(metadata.getMd5Hash())
                .build();
    }

    /**
     * Check if a file is a text file.
     *
     * @param file The file to check
     * @return true if the file is a text file, false otherwise
     */
    private boolean isTextFile(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) {
            return false;
        }
        String extension = FilenameUtils.getExtension(originalFileName);
        return "txt".equalsIgnoreCase(extension);
    }
}