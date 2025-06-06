package com.hse.storage.controller;

import com.hse.storage.dto.FileMetadataDto;
import com.hse.storage.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/storage/files")
@RequiredArgsConstructor
@Validated
public class FileController {

    private final FileService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileMetadataDto> upload(@RequestPart("file") MultipartFile file) throws Exception {
        return ResponseEntity.ok(service.upload(file));
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> download(@PathVariable UUID id) throws Exception {
        Resource res = service.download(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"%s\"".formatted(res.getFilename()))
                .contentLength(res.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(res);
    }

    @GetMapping
    public List<FileMetadataDto> list() {
        return service.list();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) throws Exception {
        service.delete(id);
    }
}