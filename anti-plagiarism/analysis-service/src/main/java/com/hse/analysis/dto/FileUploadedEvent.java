package com.hse.analysis.dto;

import java.util.UUID;

public record FileUploadedEvent(UUID fileId, String filename) { }
