package com.hse.storage.dto;

import java.util.UUID;

public record FileUploadedEvent(UUID fileId, String filename) { }
