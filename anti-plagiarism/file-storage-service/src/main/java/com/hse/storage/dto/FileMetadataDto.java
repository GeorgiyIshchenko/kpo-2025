package com.hse.storage.dto;

import com.hse.storage.model.FileEntity;

import java.time.Instant;
import java.util.UUID;

public record FileMetadataDto(
        UUID id,
        String filename,
        String contentType,
        long size,
        Instant uploadedAt
) {
    public static FileMetadataDto from(FileEntity e) {
        return new FileMetadataDto(e.getId(), e.getFilename(), e.getContentType(), e.getSize(), e.getUploadedAt());
    }
}
