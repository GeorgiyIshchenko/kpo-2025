package com.hse.storage.service;

import com.hse.storage.dto.FileMetadataDto;
import com.hse.storage.dto.FileUploadedEvent;
import com.hse.storage.model.FileEntity;
import com.hse.storage.repository.FileRepository;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class FileService {

    private final MinioClient minio;
    private final FileRepository repo;

    @Value("${minio.bucket}") private String bucket;

    private final KafkaTemplate<String, FileUploadedEvent> kafka;
    @Value("${topics.uploaded:file-uploaded}") private String topic;

    public FileMetadataDto upload(MultipartFile file) throws Exception {
        String key = UUID.randomUUID().toString();

        try (InputStream in = file.getInputStream()) {
            minio.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(key)
                            .stream(in, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        }

        FileEntity entity = new FileEntity(
                null,
                file.getOriginalFilename(),
                file.getContentType(),
                file.getSize(),
                key,
                Instant.now()
        );

        FileMetadataDto dto = FileMetadataDto.from(repo.save(entity));

        kafka.send(topic, new FileUploadedEvent(dto.id(), dto.filename()));

        return dto;
    }

    public Resource download(UUID id) throws Exception {
        FileEntity e = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("File " + id + " not found"));

        InputStream stream = minio.getObject(
                GetObjectArgs.builder().bucket(bucket).object(e.getObjectKey()).build()
        );
        return new InputStreamResource(stream) {
            @Override public String getFilename() { return e.getFilename(); }
            @Override public long contentLength()  { return e.getSize(); }
        };
    }

    public void delete(UUID id) throws Exception {
        FileEntity e = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("File " + id + " not found"));

        minio.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(e.getObjectKey()).build());
        repo.delete(e);
    }

    public List<FileMetadataDto> list() {
        return repo.findAll().stream().map(FileMetadataDto::from).toList();
    }
}
